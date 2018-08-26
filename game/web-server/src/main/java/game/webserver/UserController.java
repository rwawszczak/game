package game.webserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/api"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User create(@RequestBody User user){
        return userService.create(user);
    }

    @GetMapping(path = {"/{uid}"})
    public User findOne(@PathVariable("uid") String uid){
        return userService.findById(uid);
    }

    @PutMapping
    public User update(@RequestBody User user){
        return userService.update(user);
    }

    @DeleteMapping(path ={"/{uid}"})
    public User delete(@PathVariable("uid") String uid) {
        return userService.delete(uid);
    }

    @GetMapping
    public List findAll(){
        return userService.findAll();
    }
}
