package test;
import mvc.Controller;
import mvc.ModelAndView;
import mvc.RequestMapping;
@Controller
public class test {
	@RequestMapping("/hello")
	public ModelAndView  hello(ModelAndView mdv) {
		ModelAndView mav=mdv;
		// TODO Auto-generated constructor stub
		mav.setViewName("test");
		mav.addObject("name", mav.getMap("name"));
		mav.addObject("pas", mav.getMap("pas"));
		return mav;
	}
	@RequestMapping("/hello2")
	public ModelAndView  hello2(ModelAndView mdv) {
		ModelAndView mav =mdv;
		// TODO Auto-generated constructor stub
		mav.setViewName("test");
		return mav;
	}
}
