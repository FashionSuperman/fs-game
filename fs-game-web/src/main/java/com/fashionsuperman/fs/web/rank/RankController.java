package com.fashionsuperman.fs.web.rank;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rank")
public class RankController {
	
	@RequestMapping("/rankInit")
	public ModelAndView rankInit(){
		ModelAndView result = new ModelAndView("rank/rank");
		
		
		return result;
	}
}
