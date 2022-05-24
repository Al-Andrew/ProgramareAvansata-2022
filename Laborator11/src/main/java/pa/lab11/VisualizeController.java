package pa.lab11;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graph")
public class VisualizeController {

    @GetMapping("/circle")
    public String visualize() {
        return SocialNetworkVisualizer.exportSVG();
    }
}
