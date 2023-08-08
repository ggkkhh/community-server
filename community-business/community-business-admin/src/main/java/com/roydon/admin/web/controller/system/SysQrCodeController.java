package com.roydon.admin.web.controller.system;

import com.roydon.common.core.domain.AjaxResult;
import com.roydon.qrcode.enums.ColorEnum;
import com.roydon.qrcode.util.QRCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Api("二维码模块")
@Slf4j
@RestController
@RequestMapping("/system/qrcode")
public class SysQrCodeController {

    /**
     * 根据 content 生成二维码
     *
     * @param content
     * @return
     */
    @ApiOperation("二维码生成-base64")
    @GetMapping("/base64")
    public AjaxResult base64Code(@RequestParam("content") String content) {
        return AjaxResult.success("生成Base64二维码成功", QRCodeUtils.getBase64QRCode(content, ColorEnum.BLACK));
    }

    /**
     * 根据 content 生成二维码
     */
    @ApiOperation("二维码生成-指定内容")
    @GetMapping(value = "/get")
    public void getQRCode(HttpServletResponse response,
                          @RequestParam("content") String content) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            QRCodeUtils.getQRCode(content, ColorEnum.BLACK, stream);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * 根据 content 生成二维码
     */
    @ApiOperation("二维码生成-指定内容")
    @GetMapping(value = "/logo")
    public void logoQRCode(HttpServletResponse response,
                           @RequestParam("content") String content,
                           @RequestParam(value = "logoUrl", required = false) String logoUrl) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            QRCodeUtils.getLogoQRCode(content, logoUrl, ColorEnum.BLACK,stream);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * 根据 content 生成 color 二维码
     */
    @ApiOperation("二维码生成-指定内容")
    @GetMapping(value = "/color")
    public void getQRCode(HttpServletResponse response,
                          @RequestParam("content") String content,
                          @RequestParam("color") String color) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            QRCodeUtils.getQRCode(content,ColorEnum.find(color) , stream);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

}
