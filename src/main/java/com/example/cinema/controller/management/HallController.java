package com.example.cinema.controller.management;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.HallDeleteForm;

/**影厅管理
 * @author fjj
 * @date 2019/4/12 1:59 PM
 */
@RestController
public class HallController {
    @Autowired
    private HallService hallService;

    @RequestMapping(value = "hall/add",method = RequestMethod.POST)
    public ResponseVO addHall(@RequestBody HallForm addHallForm) {
        return hallService.addHall(addHallForm);
    }

    @RequestMapping(value = "hall/all", method = RequestMethod.GET)
    public ResponseVO searchAllHall(){
        return hallService.searchAllHall();
    }

    @RequestMapping(value="hall/{hallId}",method = RequestMethod.GET)
    public ResponseVO searchHall(@PathVariable int hallId){
        return hallService.searchHall(hallId);
    }

    @RequestMapping(value="hall/delete/batch",method = RequestMethod.DELETE)
    public ResponseVO deleteHall(@RequestBody HallDeleteForm hallDeleteForm){
        return hallService.deleteHall(hallDeleteForm);
    }
    @RequestMapping(value="hall/update",method = RequestMethod.POST)
    public ResponseVO updateHall(@RequestBody HallForm updateHallForm){
        return hallService.updateHall(updateHallForm);
    }
}
