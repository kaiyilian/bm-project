package com.bumu.bran.admin.corporation.service.impl;

import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.admin.corporation.service.FileService;
import com.bumu.common.service.ReadFileResponseService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/5/18
 */
@Service
public class FileServiceImpl implements FileService {

    String CORP_INFO_FILE_PREFIX = "/admin/corporation/info/";

    String IMAGE_DIRECTORY_NAME = "images";

    private static final int imageDataReadLength = 200 * 1024;//每次读取200k
    private static final int htmlDataReadLength = 400 * 1024;//每次读取400k

    @Autowired
    ReadFileResponseService readFileResponseService;

    @Autowired
    BranAdminConfigService branAdminConfigService;
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public void readEmployeeIdcardImageFile(String fileName, String branUserId, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        String path = branAdminConfigService.getInductionUploadPath(branUserId) + fileName + ".jpg";
        if (!new File(path).exists()) {
            path = branAdminConfigService.getServletContext().getRealPath("/") + "image" + File.separator + "attachment_empty.jpg";
        }
        logger.debug("path: " + path);
        readFileResponseService.readFileToResponse(path, imageDataReadLength, response);
    }

    @Override
    public void readEmployeeEducationImageFile(String fileName, String branUserId, HttpServletResponse response)
            throws Exception {
        response.setContentType("image/jpeg");
        String path = branAdminConfigService.getCertUploadPath(branUserId) + fileName + ".jpg";
        if (!new File(path).exists()) {
            path = branAdminConfigService.getServletContext().getRealPath("/") + "image" + File.separator + "attachment_empty.jpg";
        }
        logger.debug("path: " + path);
        readFileResponseService.readFileToResponse(path, imageDataReadLength, response);
    }

    @Override
    public void readEmployeeLeaveImageFile(String fileName, String branUserId, HttpServletResponse response)
            throws Exception {
        response.setContentType("image/jpeg");
        String path = branAdminConfigService.getLeaveUploadPath(branUserId) + fileName + ".jpg";
        logger.info("------------------------path1:" + path);
        if (!new File(path).exists()) {
            path = branAdminConfigService.getServletContext().getRealPath("/") + "image" + File.separator + "attachment_empty.jpg";
        }
        logger.info("------------------------path2:" + path);
        readFileResponseService.readFileToResponse(path, imageDataReadLength, response);
    }

    @Override
    public void readEmployeeFaceImageFile(String fileName, String branUserId, HttpServletResponse response)
            throws Exception {
        response.setContentType("image/jpeg");
        String path = branAdminConfigService.getFaceFileUploadPath(branUserId) + fileName + ".jpg";
        logger.info("face path1: " + path);
        if (!new File(path).exists()) {
            path = branAdminConfigService.getServletContext().getRealPath("/") + "image" + File.separator + "face_empty.jpg";
        }
        logger.info("face path2: " + path);
        readFileResponseService.readFileToResponse(path, imageDataReadLength, response);
    }

    @Override
    public String getEmployeeAllKindImageURL(String route, String fileName, String branUserId) {
        String url = branAdminConfigService.getBranAdminResourceServer() + route + "?" + "file_name=" + fileName + "&bran_user_id=" + branUserId;
        logger.debug("getEmployeeAllKindImageURL: " + url);
        return url;
    }

    @Override
    public void convertWord2Html(String wordPath, String htmlSavePath, String wordFileName, String imageUrlLocation) throws TransformerException, IOException,
            ParserConfigurationException, OfficeXmlFileException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        //生成导出目录名
        //html文件导出目录
        String thisHtmlExportLocation = htmlSavePath;
        File thisHtmlExportLocationDirectory = new File(thisHtmlExportLocation);
        if (!thisHtmlExportLocationDirectory.exists() && !thisHtmlExportLocationDirectory.isDirectory()) {
            thisHtmlExportLocationDirectory.mkdirs();
        }
        File thisHtmlExportImageDirectory = new File(thisHtmlExportLocation + IMAGE_DIRECTORY_NAME + File.separator);
        if (!thisHtmlExportImageDirectory.exists() && !thisHtmlExportImageDirectory.isDirectory()) {
            thisHtmlExportImageDirectory.mkdirs();
        }
        try {
            HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(wordPath + wordFileName));//WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                @Override
                public String savePicture(byte[] bytes, PictureType pictureType, String s, float v, float v1) {
                    return imageUrlLocation + s;
                }
            });
            wordToHtmlConverter.processDocument(wordDocument);
            //save pictures
            List pics = wordDocument.getPicturesTable().getAllPictures();
            String imageFileLocation = thisHtmlExportLocation + IMAGE_DIRECTORY_NAME + File.separatorChar;
            if (pics != null) {
                for (int i = 0; i < pics.size(); i++) {
                    Picture pic = (Picture) pics.get(i);
                    try {
                        pic.writeImageContent(new FileOutputStream(imageFileLocation + pic.suggestFullFileName()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            Document htmlDocument = wordToHtmlConverter.getDocument();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(out);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
            out.close();
            String content = new String(out.toByteArray());
            logger.debug("厂车路线保存html的路径: " + thisHtmlExportLocation);
            FileUtils.write(new File(thisHtmlExportLocation, "index.html"), content, "UTF-8");
        } catch (OfficeXmlFileException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readCorpBusPreviewHtmlFile(String branCorpId, String suffix, HttpServletResponse response) {
        String filePath = branAdminConfigService.getCorpBusHtmlPreviewPath(branCorpId) + "index" + suffix;
        readFileResponseService.readFileToResponse(filePath, htmlDataReadLength, response);
    }

    @Override
    public void readCorpBusPreviewHtmlImageFile(String fileName, String corpId, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        readFileResponseService.readFileToResponse(branAdminConfigService.getCorpBusHtmlPreviewPath(corpId) + "images" + File.separator + fileName, imageDataReadLength, response);
    }

    @Override
    public String generateBusHtmlUrl(String branCorpId, String suffix) {
        if (".pdf".equals(suffix)) {
            return branAdminConfigService.getBranAdminResourceServer() + "/pdf/web/viewer.html";
        }
        return branAdminConfigService.getBranAdminResourceServer() + CORP_INFO_FILE_PREFIX + "bus/html?suffix=" + suffix;
    }

    public String generateBusHtmlPreviewUrl(String branCorpId, String suffix) {
        if (".pdf".equals(suffix)) {
            return branAdminConfigService.getBranAdminResourceServer() + "/pdf/web/viewer.html";
        }
        return branAdminConfigService.getBranAdminResourceServer() + CORP_INFO_FILE_PREFIX + "bus/preview/html?suffix=" + suffix;
    }

    public String generateBusHtmlPreviewImageUrl(String branCorpId) {
        return branAdminConfigService.getBranAdminResourceServer() + CORP_INFO_FILE_PREFIX + "bus/preview/img?file_name=";
    }

    @Override
    public String generateBusHtmlImageUrl(String branCorpId) {
        return branAdminConfigService.getBranAdminResourceServer() + CORP_INFO_FILE_PREFIX + "bus/img?bran_corp_id=" + branCorpId + "&file_name=";
    }

    @Override
    public void readCorpHandBookPreviewHtmlFile(String branCorpId, String suffix, HttpServletResponse response) {
        String filePath = branAdminConfigService.getCorpHandBookHtmlPreviewPath(branCorpId) + "index" + suffix;
        readFileResponseService.readFileToResponse(filePath, htmlDataReadLength, response);
    }

    @Override
    public String generateHandBookHtmlPreviewImageUrl(String branCorpId) {
        return branAdminConfigService.getBranAdminResourceServer() + CORP_INFO_FILE_PREFIX + "handbook/preview/img?file_name=";
    }

    @Override
    public String generateHandBookHtmlImageUrl(String branCorpId) {
        return branAdminConfigService.getBranAdminResourceServer() + CORP_INFO_FILE_PREFIX + "handbook/img?bran_corp_id=" + branCorpId + "&file_name=";
    }

    @Override
    public void readCorpHandBookPreviewHtmlImageFile(String fileName, String corpId, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        readFileResponseService.readFileToResponse(branAdminConfigService.getCorpHandBookHtmlPreviewPath(corpId) + "images" + File.separator + fileName, imageDataReadLength, response);
    }

    @Override
    public String generateHandBookHtmlUrl(String branCorpId, String suffix) {
        if (".pdf".equals(suffix)) {
            return branAdminConfigService.getBranAdminResourceServer() + "/pdf/web/viewer.html";
        }
        return branAdminConfigService.getBranAdminResourceServer() + CORP_INFO_FILE_PREFIX + "handbook/html?suffix=" + suffix;
    }

    @Override
    public String generateHandBookHtmlPreviewUrl(String branCorpId, String suffix) {
        if (".pdf".equals(suffix)) {
            return branAdminConfigService.getBranAdminResourceServer() + "/pdf/web/viewer.html";
        }
        return branAdminConfigService.getBranAdminResourceServer() + CORP_INFO_FILE_PREFIX + "handbook/preview/html?suffix=" + suffix;
    }

    @Override
    public String getBranCorpImageUrl(String fileName) {
        return branAdminConfigService.getBranAdminResourceServer() + CORP_INFO_FILE_PREFIX + "detail/image?file_name=" + fileName;
    }

    @Override
    public void readCorpDetailImageFile(String fileName, String branCorpId, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        String filePath = branAdminConfigService.getCorpOtherImageUploadPath(branCorpId) + fileName + ".jpg";
        readFileResponseService.readFileToResponse(filePath, imageDataReadLength, response);
    }

    @Override
    public String generateCorpCheckinQRCodeUrl(String code) {
        return branAdminConfigService.getBranAdminResourceServer() + CORP_INFO_FILE_PREFIX + "detail/qrcode?code=" + code;
    }

    @Override
    public String getEmpIdCardFontImg(String fileName, String empId, String corpId) {
        return branAdminConfigService.getBranAdminResourceServer() + EMPLOYEE_ALL_PHOTO + "?fileName=" + fileName + "&empId=" + empId + "&corpId=" + corpId;
    }

    @Override
    public String getEmpIdCardBackImg(String fileName, String empId, String corpId) {
        return branAdminConfigService.getBranAdminResourceServer() + EMPLOYEE_ALL_PHOTO + "?fileName=" + fileName + "&empId=" + empId + "&corpId=" + corpId;
    }

    @Override
    public String getEmpIdCardFaceImg(String fileName, String empId, String corpId) {
        return branAdminConfigService.getBranAdminResourceServer() + EMPLOYEE_ALL_PHOTO + "?fileName=" + fileName + "&empId=" + empId + "&corpId=" + corpId;
    }

    @Override
    public String getEmpFaceImg(String fileName, String empId, String corpId) {
        return StringUtils.isAnyBlank(fileName) ? null : branAdminConfigService.getBranAdminResourceServer() + EMPLOYEE_ALL_PHOTO + "?fileName=" + fileName + "&empId=" + empId + "&corpId=" + corpId;
    }

    @Override
    public String getEmpLeaveCertImg(String fileName, String empId, String corpId) {
        return branAdminConfigService.getBranAdminResourceServer() + EMPLOYEE_ALL_PHOTO + "?fileName=" + fileName + "&empId=" + empId + "&corpId=" + corpId;
    }

    @Override
    public String getEmpEducationImg(String fileName, String empId, String corpId) {
        return branAdminConfigService.getBranAdminResourceServer() + EMPLOYEE_ALL_PHOTO + "?fileName=" + fileName + "&empId=" + empId + "&corpId=" + corpId;
    }

    @Override
    public void getEmpAllImage(String fileName, String empId, String corpId, HttpServletResponse response)
            throws Exception {
        response.setContentType("image/jpeg");
        readFileResponseService.readFileToResponse(branAdminConfigService.getCorpPhotoPath(corpId, empId) + File.separator + fileName + ".jpg", imageDataReadLength, response);
    }

    public String getEmpAllImageUrl(String fileName, String branUserId, String type) {
        return branAdminConfigService.getBranAdminResourceServer() + PRE_ALL_PHOTO + "?fileName=" + fileName + "&branUserId=" + branUserId + "&type=" + type;
    }

    @Override
    public void getEmployeeImage(String fileName, String branUserId, String type, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        String path = null;
        if ("bank".equals(type)) {
            path = branAdminConfigService.getBankCardUploadPath(branUserId) + fileName + ".jpg";

        }
        if (!new File(path).exists()) {
            path = branAdminConfigService.getServletContext().getRealPath("/") + "image" + File.separator + "attachment_empty.jpg";
        }
        logger.debug("path: " + path);
        readFileResponseService.readFileToResponse(path, imageDataReadLength, response);
    }
}
