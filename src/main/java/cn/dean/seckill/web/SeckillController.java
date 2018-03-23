package cn.dean.seckill.web;

import cn.dean.seckill.dto.Exposure;
import cn.dean.seckill.dto.SeckillExecution;
import cn.dean.seckill.dto.SeckillResult;
import cn.dean.seckill.entity.Seckill;
import cn.dean.seckill.enums.SeckillStatEnum;
import cn.dean.seckill.exception.RepeatSeckillException;
import cn.dean.seckill.exception.SeckillCloseException;
import cn.dean.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(name = "seckill")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("seckillList", seckillList);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SeckillResult<Exposure> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposure> result;
        try {
            Exposure exposure = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposure>(true, exposure);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SeckillResult<Exposure>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long userPhone) {
        if (userPhone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
            result = new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatSeckillException e) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            result = new SeckillResult<SeckillExecution>(false, execution);
        } catch (SeckillCloseException e) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            result = new SeckillResult<SeckillExecution>(false, execution);
        } catch (Exception e) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            logger.error(e.getMessage(), e);
            result = new SeckillResult<SeckillExecution>(false, execution);
        }
        return result;
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult<Long>(true, now.getTime());
    }
}
