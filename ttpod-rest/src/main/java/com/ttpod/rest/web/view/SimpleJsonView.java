package com.ttpod.rest.web.view;

import com.ttpod.rest.common.doc.ParamKey;
import com.ttpod.rest.common.util.JSONUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 *  转换为json 输出,处理 callBack
 * date: 12-8-21 下午2:08
 *
 * @author: yangyang.cong@ttpod.com
 */
//@CompileStatic
public class SimpleJsonView implements View {

    private static final SimpleJsonView instance = new SimpleJsonView();

    protected SimpleJsonView(){

    }


    @Override
    public String getContentType() {
        return jsonType;
    }

    static final String jsonType = "text/plain;charset=utf-8";

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String json = toJson(model);
        String callback = request.getParameter(ParamKey.In.callback);
        if(null != callback && callback.length() > 0){
            rennderJson(callback+'('+json+')',"application/x-javascript;charset=utf-8",response);
            return;
        }
        rennderJson(json,response);
        //JSONUtil.printObj(response,json);
    }

    protected String toJson(Map<String, ?> model){
        return  JSONUtil.beanToJson(model);
    }

    public static void rennderJson(String json, HttpServletResponse response) throws IOException {
        rennderJson(json, jsonType,response);
    }
    public static void rennderJson(String json,  String contentType ,HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType(contentType);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.close();
    }

    public static ModelAndView asJson(Map o){
        return new ModelAndView(instance,o);
    }
}
