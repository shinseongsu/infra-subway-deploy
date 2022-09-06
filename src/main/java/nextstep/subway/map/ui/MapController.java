package nextstep.subway.map.ui;

import static net.logstash.logback.argument.StructuredArguments.kv;

import nextstep.subway.map.application.MapService;
import nextstep.subway.map.dto.PathResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapController {
    private static final Logger log = LoggerFactory.getLogger(MapController.class);
    private static final Logger fileLogger = LoggerFactory.getLogger("file");

    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/paths")
    public ResponseEntity<PathResponse> findPath(@RequestParam Long source, @RequestParam Long target) {

        // 테스트
        log.error("An ERROR Message");
        fileLogger.info("파일 로깅 입니다.");

        log.info("{}, {}",
            kv("출발지", source),
            kv("도착지", target)
        );

        return ResponseEntity.ok(mapService.findPath(source, target));
    }
}
