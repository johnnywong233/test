package provider;

import contract.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Johnny
 * Date: 2017/11/21
 * Time: 11:27
 */
@RestController
public class SingerRestController {
    @Resource
    private SingerRepository singerRepository;

    @RequestMapping(path = "/api/singers")
    public Page getCustomers() {
        Page page = new Page();
        page.setData(singerRepository.findAll());
        return page;
    }
}
