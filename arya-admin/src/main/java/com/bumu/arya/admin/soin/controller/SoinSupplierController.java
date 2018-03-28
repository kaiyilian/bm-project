package com.bumu.arya.admin.soin.controller;

import com.bumu.arya.admin.soin.controller.command.CreateOrUpdateSoinSupplierCommand;
import com.bumu.arya.admin.soin.controller.command.IdListCommand;
import com.bumu.arya.admin.soin.controller.command.SoinDistrictAddSoinSupplierCommand;
import com.bumu.arya.admin.soin.service.SoinSupplierService;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by CuiMengxin on 16/8/2.
 * 社保供应商管理
 */
@Controller
public class SoinSupplierController {

    @Autowired
    SoinSupplierService soinSupplierService;


    /**
     * 供应商管理页面
     *
     * @return
     * @deprecated
     */
    @RequestMapping(value = "/admin/soin/suppliers/manage/index", method = RequestMethod.GET)
    public String suppliersManagePage() {
        return "order/supplier_manage";
    }

    /**
     * 获取已开通地区列表
     *
     * @return
     */
    @RequestMapping(value = "/admin/suppliers/setting/soin/district/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse getSoinDistricts() {
        try {
            return new HttpResponse(soinSupplierService.getAllSoinDistricts());
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 获取全部供应商列表
     *
     * @return
     */
    @RequestMapping(value = "/admin/suppliers/setting/list/all", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse getAllSuppliers(@RequestParam(value = "page", defaultValue = "1") int page,
                                 @RequestParam(value = "page_size", defaultValue = "10") int pageSize) {
        try {
            return new HttpResponse(soinSupplierService.getAllSoinSuppliers(page - 1, pageSize));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 新增供应商
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/suppliers/setting/create", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse addSoinSupplier(@RequestBody CreateOrUpdateSoinSupplierCommand command) {
        try {
            return new HttpResponse(soinSupplierService.createSoinSupplier(command));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 更新供应商
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/suppliers/setting/update", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse updateSoinSupplier(@RequestBody CreateOrUpdateSoinSupplierCommand command) {
        try {
            soinSupplierService.updateSoinSupplier(command);
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 删除供应商
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/suppliers/setting/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse deleteSoinSupplier(@RequestBody IdListCommand command) {
        try {
            soinSupplierService.deleteSoinSupplier(command);
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 获取某地区的所有供应商
     *
     * @param districtId
     * @return
     */
    @RequestMapping(value = "/admin/suppliers/setting/district/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse querySoinDistrictSuppliers(@RequestParam("district_id") String districtId) {
        try {
            return new HttpResponse(soinSupplierService.querySoinDistrictAllSuppliers(districtId));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 获取某地区尚未添加的供应商
     *
     * @param districtId
     * @return
     */
    @RequestMapping(value = "/admin/suppliers/setting/district/unused/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse querySoinDistrictUnusedSuppliers(@RequestParam("district_id") String districtId,
                                                  @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "page_size", defaultValue = "10") int pageSize) {
        try {
            return new HttpResponse(soinSupplierService.querySoinDistrictAllUnusedSuppliers(districtId, page - 1, pageSize));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 为某地区添加供应商
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/suppliers/setting/district/add", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse addSupplierToSoinDistrict(@RequestBody SoinDistrictAddSoinSupplierCommand command) {
        try {
            soinSupplierService.addSupplierToSoinDistrict(command.getDistrictId(), command.getSupplierId());
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 移除地区的供应商
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/suppliers/setting/district/remove", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse removeSupplierToSoinDistrict(@RequestBody SoinDistrictAddSoinSupplierCommand command) {
        try {
            soinSupplierService.removeSupplierToSoinDistrict(command.getDistrictId(), command.getSupplierId());
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 为某地区设置首选供应商
     *
     * @param command
     * @return
     */
    @RequestMapping(value = "/admin/suppliers/setting/district/preferred", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse preferredSupplierToSoinDistrict(@RequestBody SoinDistrictAddSoinSupplierCommand command) {
        try {
            soinSupplierService.setSetSoinDistrctPrimarySupplier(command.getDistrictId(), command.getSupplierId());
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }
}
