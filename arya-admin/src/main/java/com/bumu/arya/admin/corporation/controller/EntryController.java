package com.bumu.arya.admin.corporation.controller;

import com.bumu.SysUtils;
import com.bumu.arya.admin.corporation.controller.command.CreateUpdateCorpAdminCommand;
import com.bumu.arya.admin.corporation.service.CorporationService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import com.bumu.common.service.QRCodeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by CuiMengxin on 16/7/19.
 */
@Controller
public class EntryController {

    @Autowired
    private CorporationService corporationService;

    @Autowired
    QRCodeService qrCodeService;

    /**
     * 获取入职管理的组织树
     *
     * @return
     */
    @RequestMapping(value = "/admin/entry/organization/tree", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse getEntryOrganizationTree() {
        try {
            return new HttpResponse(corporationService.generateEntryOrganizationTree());
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }


    /**
     * 查询公司的企业管理员
     *
     * @param corpId
     * @return
     */
    @RequestMapping(value = "/admin/entry/corporation/admin/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse getCorpAdminList(@RequestParam("corp_id") String corpId) {
        try {
            return new HttpResponse(corporationService.getCorpAdminList(corpId));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 新增或修改企业管理员
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/entry/corporation/admin/create_update", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse addOrUpdateCorpAdmin(@RequestBody CreateUpdateCorpAdminCommand command) {
        try {
            if (StringUtils.isNotBlank(command.getEmail())) {
                if (!SysUtils.checkEmail(command.getEmail())) {
                    throw new AryaServiceException(ErrorCode.CODE_VALID_EMAIL_WRONG);
                }
            }

            Session session = SecurityUtils.getSubject().getSession();
            if (session.getAttribute("user_id") == null) {
                throw new AryaServiceException(ErrorCode.CODE_NEED_LOGIN);
            }
            String currentUser = session.getAttribute("user_id").toString();
            if (StringUtils.isAnyBlank(command.getId())) {
                corporationService.addCorpAdmin(command, currentUser);
            } else {
                corporationService.updateCorpAdmin(command, currentUser);
            }
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    //不做删除管理员

    /**
     * 获取企业入职码
     *
     * @param corpId
     * @return
     */
    @RequestMapping(value = "/admin/entry/corporation/check_in_code", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse getCorpCheckInCode(@RequestParam("corp_id") String corpId) {
        try {
            return new HttpResponse(corporationService.getCorpCheckInCodeAndQRCode(corpId));

        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 获取企业入职二维码图片
     *
     * @param response
     */
    @RequestMapping(value = "/admin/entry/corporation/qrcode", method = RequestMethod.GET)
    public
    @ResponseBody
    void getCorpInfoDetailQRCodeImage(@RequestParam String code, HttpServletResponse response) {
        try {
            OutputStream stream = response.getOutputStream();
            ImageIO.write(qrCodeService.createImage(code), "jpg", stream);
            stream.close();
        } catch (AryaServiceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
