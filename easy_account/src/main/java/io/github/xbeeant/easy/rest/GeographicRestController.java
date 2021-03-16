package io.github.xbeeant.easy.rest;

import io.github.xbeeant.core.ApiResponse;
import io.github.xbeeant.easy.core.model.Geographic;
import io.github.xbeeant.easy.core.service.IGeographicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 行政区域查询
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2021/01/09
 */
@RestController
@RequestMapping("api/geographic")
@Api(tags = "位置服务")
public class GeographicRestController {
    private IGeographicService geographicService;

    /**
     * 城市、地区、街道
     *
     * @param adcode 区域编码
     * @return {@link ApiResponse<List< Geographic >>}
     */
    @ApiOperation(value = "城市", notes = "")
    @GetMapping(value = {"city/{adcode}", "district/{adcode}", "street/{adcode}"})
    public ApiResponse<List<Geographic>> city(@ApiParam(value = "广告代码", required = true, example = "") @PathVariable("adcode") String adcode) {
        Geographic example = new Geographic();
        example.setAdcode(adcode);

        ApiResponse<Geographic> geographicApiResponse = geographicService.selectOneByExample(example);

        example = new Geographic();
        example.setPid(geographicApiResponse.getData().getId());

        List<String> orders = new ArrayList<>();
        orders.add("adcode asc");
        return geographicService.selectAllByExample(example, orders);
    }

    /**
     * 省
     *
     * @return {@link ApiResponse<List<Geographic>>}
     */
    @ApiOperation(value = "省", notes = "")
    @GetMapping("province")
    public ApiResponse<List<Geographic>> province() {
        Geographic example = new Geographic();
        example.setLevel("province");

        List<String> orders = new ArrayList<>();
        orders.add("adcode asc");
        return geographicService.selectAllByExample(example, orders);
    }

    /**
     * 重新同步
     *
     * @param file 文件
     * @return {@link ApiResponse<String>}
     * @throws IOException ioexception
     */
    @ApiOperation(value = "重新同步", notes = "")
    @PostMapping("resync")
    public ApiResponse<String> resync(@ApiParam(value = "文件", required = true, example = "") MultipartFile file) throws IOException {
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        content = content.replaceAll("\"citycode\": \\[\\]", "\"citycode\": \"\"");
        return geographicService.resync(content);
    }

    @Autowired
    public void setGeographicService(IGeographicService geographicService) {
        this.geographicService = geographicService;
    }
}
