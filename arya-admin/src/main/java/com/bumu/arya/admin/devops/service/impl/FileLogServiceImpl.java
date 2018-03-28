package com.bumu.arya.admin.devops.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.devops.controller.command.OnlineDownloadCommand;
import com.bumu.arya.admin.devops.result.OnlineLogPageResult;
import com.bumu.arya.admin.devops.result.ProjectResult;
import com.bumu.arya.admin.devops.service.FileLogService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.service.ConfigService;
import com.bumu.common.service.ReadFileResponseService;
import com.bumu.exception.AryaServiceException;
import com.bumu.utils.DateUtil;
import com.google.gson.Gson;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileFilter;
import java.util.*;

import static com.bumu.arya.common.OperateConstants.ARYA_ADMIN_LOG_DOWNLOAD;
import static com.bumu.common.service.impl.CommonFileServiceImpl.fileDateReadLength;

/**
 * @author Guchaochao
 * @date 2018/1/10
 */
@Service
public class FileLogServiceImpl implements FileLogService {

    private static Logger logger = LoggerFactory.getLogger(FileLogServiceImpl.class);

    @Autowired
    private ConfigService configService;

    @Autowired
    private ReadFileResponseService readFileResponseService;

    @Autowired
    private OpLogService opLogService;

    private static String PROJECT_CONFIG_KEY = "arya.online_log_instances";

    private static String ONLINE_LOG_RECENT_DAYS_CONFIG_KEY = "arya.online_log_recent_days";

    private static String PROJECT_ROOT_PATH_CONFIG_VALUE = "/mnt";

    public static final String LOG_FILE_URL = "admin/online/log/download";

    private static long kb = 1 * 1024;

    @Override
    public List<ProjectResult> projectList() {
        List<Map<String, String>> projectList = projectListInDB();
        List<ProjectResult> resultList = new ArrayList<>();
        projectList.stream().forEach(map -> {
            ProjectResult result = new ProjectResult();
            result.setProjectCode(map.get("projectCode"));
            result.setProjectName(map.get("projectName"));
            resultList.add(result);
        });
        return resultList;
    }

    private List<Map<String, String>> projectListInDB() {
        String projectConfig = configService.getConfigByKey(PROJECT_CONFIG_KEY);
        System.out.println(projectConfig);
        List<Map<String, String>> projectList = new Gson().fromJson(projectConfig, ArrayList.class);
        return projectList;
    }

    @Override
    public OnlineLogPageResult logPage(String projectCode, Integer page, Integer pageSize) throws Exception {
        OnlineLogPageResult result = new OnlineLogPageResult();

        //查找到项目的配置
        List<Map<String, String>> projectList = projectListInDB();
        Map<String, String> projectMap = new HashMap<>();
        for (Map<String, String> map : projectList) {
            if (projectCode.equals(map.get("projectCode"))) {
                projectMap = map;
            }
        }
        if (projectMap.isEmpty()) {
            return result;
        }

        Integer recentDays = configService.getIntKey(ONLINE_LOG_RECENT_DAYS_CONFIG_KEY);
        Integer indexDay = 0;
        List<OnlineLogPageResult.OnlineLogReuslt> resultList = new ArrayList<>();
        while(indexDay <= recentDays) {
            resultList.addAll(getList(
                    DateUtil.addDay(new Date(), - indexDay),
                    PROJECT_ROOT_PATH_CONFIG_VALUE + "/" + projectMap.get("projectPath") + "/logs",
                    projectMap.get("projectName")));
            indexDay++;
        }
        resultList.sort(new Comparator<OnlineLogPageResult.OnlineLogReuslt>() {
            @Override
            public int compare(OnlineLogPageResult.OnlineLogReuslt o1, OnlineLogPageResult.OnlineLogReuslt o2) {
                if (o1.getFileDate() < o2.getFileDate()) {
                    return 1;
                } else {
                    return -1;
                }

            }
        });

        // 取分页数据
        if (null != page && null != pageSize) {
            int total = resultList.size();
            result.setPages(Utils.calculatePages(total, pageSize));
            result.setOnlineLogResults(resultList.subList(pageSize * (page - 1), ((pageSize * page) > total ? total : (pageSize * page))));
            result.setTotalRows(total);
        }

        return result;
    }

    /**
     * 获取目录下当前日期下的所有文件
     * @param date
     * @return
     */
    private List<OnlineLogPageResult.OnlineLogReuslt> getList(Date date, String projectPath, String projectName) throws Exception{
        logger.info("查询日志的路径-》" + projectPath);
        String yyyyMM = new DateTime(date).toString("yyyy-MM");
        String yyyyMMdd = new DateTime(date).toString("yyyy-MM-dd");
        File parentPath = new File(projectPath);
        File[] filesCurrentDir = parentPath.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().indexOf(projectName + "-" + yyyyMMdd) == -1) {
                    return false;
                }
                return true;
            }
        });
        List<OnlineLogPageResult.OnlineLogReuslt> resultList = new ArrayList<>();
        Date nowDate = new Date();
        //如果是今天，还需要家一个当前时间的日志文件
        if (DateUtils.isSameDay(date, nowDate)) {
            File file = new File(projectPath + "/" + projectName + ".log");
            if (file.exists()) {
                OnlineLogPageResult.OnlineLogReuslt result = new OnlineLogPageResult.OnlineLogReuslt();
                Date fileDate = DateUtils.parseDate(new DateTime(nowDate).toString("yyyy-MM-dd HH"), "yyyy-MM-dd HH");
                result.setFileDate(fileDate.getTime());
                result.setFileHour(String.valueOf(fileDate.getHours()));
                result.setFileName(file.getName());
                result.setFilePart("1");
                result.setFileSize((file.length() / kb) + "kb");
                resultList.add(result);
            }
        }

        parentPath = new File(projectPath + "/" + yyyyMM);
        File[] filesMonthDir = parentPath.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().indexOf(projectName + "-" + yyyyMMdd) == -1) {
                    return false;
                }
                return true;
            }
        });
        List<File> fileList = new ArrayList<>();
        fileList.addAll(null != filesCurrentDir && filesCurrentDir.length > 0 ? Arrays.asList(filesCurrentDir) : new ArrayList<File>());
        fileList.addAll(null != filesMonthDir && filesMonthDir.length > 0 ? Arrays.asList(filesMonthDir) : new ArrayList<File>());

        for (File file : fileList) {
            String[] parts =  file.getName().split("\\.")[0].split("-");
            OnlineLogPageResult.OnlineLogReuslt result = new OnlineLogPageResult.OnlineLogReuslt();
            Date fileDate = DateUtils.parseDate(
                    parts[1] + "-" + parts[2] + "-" + parts[3].split("_")[0] + " " + parts[3].split("_")[1] + ":00:00", "yyyy-MM-dd HH:mm:ss");
            result.setFileDate(fileDate.getTime());
            result.setFileHour(parts[3].split("_")[1]);
            result.setFileName(file.getName());
            result.setFilePart(parts[4]);
            result.setFileSize((file.length() / kb) + "kb");
            resultList.add(result);
        }

        return resultList;
    }

    @Override
    public FileUploadFileResult downloadUrl(OnlineDownloadCommand onlineDownloadCommand) throws Exception {

        FileUploadFileResult result = new FileUploadFileResult();
        List<Map<String, String>> projectList = projectListInDB();
        Map<String, String> projectMap = new HashMap<>();
        for (Map<String, String> map : projectList) {
            if (onlineDownloadCommand.getProjectCode().equals(map.get("projectCode"))) {
                projectMap = map;
            }
        }
        if (projectMap.isEmpty()) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "项目不存在");
        }

        String rootPath = PROJECT_ROOT_PATH_CONFIG_VALUE;
        File file = null;
        if (DateUtils.isSameDay(new Date(onlineDownloadCommand.getFileDate()), new Date())
                && new DateTime(onlineDownloadCommand.getFileDate()).getHourOfDay() == new DateTime().getHourOfDay()) {
            file = new File(rootPath + "/" + projectMap.get("projectPath") + "/logs/" + onlineDownloadCommand.getFileName());
        } else {
            file = new File(rootPath + "/" + projectMap.get("projectPath") + "/logs/" + new DateTime(onlineDownloadCommand.getFileDate()).toString("yyyy-MM")
                    + "/" + onlineDownloadCommand.getFileName());
        }
        if (null == file || !file.exists()) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "不存在该日志文件");
        }
        result.setUrl(LOG_FILE_URL + "?filePath=" + file.getPath());
        StringBuffer logInfo = new StringBuffer("下载了日志文件《" + file.getName() + "》");
        opLogService.successLog(ARYA_ADMIN_LOG_DOWNLOAD, logInfo, logger);
        return result;
    }

    @Override
    public void download(String path, HttpServletResponse response) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "文件不存在");
        }
        response.setContentType("APPLICATION/OCTET-STREAM");
        String headStr = "attachment;filename=\"" + SysUtils.parseGBK(file.getName()) + "\"";
        response.setHeader("Content-Disposition", headStr);
        readFileResponseService.readFileToResponse(path,fileDateReadLength, response);
    }
}
