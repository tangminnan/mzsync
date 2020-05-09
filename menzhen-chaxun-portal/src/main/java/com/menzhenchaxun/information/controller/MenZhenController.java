package com.menzhenchaxun.information.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.menzhenchaxun.information.domain.menzhen.menzhenDO;
import com.menzhenchaxun.information.domain.shujuchaxun.shujuchaxunDO;
import com.menzhenchaxun.information.service.menzhen.service.menzhenService;
import com.menzhenchaxun.information.service.shujuchaxun.service.shujuchaxunService;

@Controller
@RequestMapping("/menzhenshuju/chaxun")
public class MenZhenController {
	
	@Autowired
	private menzhenService menzhenService;
	@Autowired
	private shujuchaxunService shujuchaxunService;
	
	@ResponseBody
	@PostMapping("/chaxunsave")
	Map<String,Object> chaxunsave(String SMECIMemberId) throws ParseException{
		Map<String, Object> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		menzhenDO memberCard = menzhenService.getMemberCard(SMECIMemberId);
		if(memberCard == null){
			shujuchaxunDO member = shujuchaxunService.getMemberId(SMECIMemberId);
			if(member!=null) {
				menzhenDO mz = new menzhenDO();
				mz.setUname(member.getSMECIName());
				mz.setUorganization("学校");
				mz.setUgender(Integer.valueOf(member.getSMECISex()));
				mz.setUgrand("年级");
				String format = sdf.format(member.getSMECIBirthday());
				mz.setUbirthday(sdf.parse(format));
				Integer age = Integer.valueOf((sdf.format(new Date()).substring(0, 4))) - Integer.valueOf((sdf.format(member.getSMECIBirthday()).substring(0, 4)));
				mz.setUage(age);
				mz.setUidcard(member.getSMECIIdentityCard());
				mz.setUphone(member.getSMECIPhone());
				mz.setMname(member.getSMECIName());
				mz.setUupdatedate(member.getSMECIRegisterDate());
				mz.setMemberCard(SMECIMemberId);
				if (menzhenService.save(mz) > 0) {
					map.put("code", "0");
					map.put("data", mz);
					map.put("msg", "添加成功");
				}
			}else{
				map.put("code", "-1");
				map.put("msg", "未找到数据，可能是卡号错误");
			}

		}else{
			map.put("code", "-1");
			map.put("data", memberCard);
			map.put("msg", "已存在");
		}
		return map;
	}

	
	
}
