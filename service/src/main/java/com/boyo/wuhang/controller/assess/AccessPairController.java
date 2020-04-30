package com.boyo.wuhang.controller.assess;

import com.boyo.wuhang.entity.assess.WhAccessPair;
import com.boyo.wuhang.service.assess.AccessPairService;
import com.boyo.wuhang.ultity.JsonBuilder;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/access/pair")
public class AccessPairController {
	@Autowired
	private AccessPairService pairService;

	@RequestMapping(value = "list",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getAccessPairList(@RequestBody JSONObject jsonObject){
		List<WhAccessPair> list = pairService.getPairList();
		return JsonBuilder.build(0,"",list);
	}

}
