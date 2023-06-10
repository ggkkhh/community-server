package com.roydon.admin.web.controller.system;

import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.utils.qrcode.QRCodeUtil;
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
public class SystemQrCodeController {

    /**
     * 根据 content 生成二维码
     *
     * @param content
     * @param width
     * @param height
     * @return
     */
    @ApiOperation("二维码生成-base64")
    @GetMapping("/getBase64")
    public AjaxResult getQRCode(@RequestParam("content") String content,
                                @RequestParam(value = "logoUrl", required = false) String logoUrl,
                                @RequestParam(value = "width", required = false) Integer width,
                                @RequestParam(value = "height", required = false) Integer height) {
        return AjaxResult.success("生成Base64二维码成功", QRCodeUtil.getBase64QRCode(content, logoUrl));
    }

    /**
     * 根据 content 生成二维码
     */
    @ApiOperation("二维码生成-指定内容")
    @GetMapping(value = "/getQrCode")
    public void getQRCode(HttpServletResponse response,
                          @RequestParam("content") String content,
                          @RequestParam(value = "logoUrl", required = false) String logoUrl) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            QRCodeUtil.getQRCode(content, logoUrl, stream);
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
