package ba.unsa.etf.chatmicroservice.controllers;

import ba.unsa.etf.chatmicroservice.models.Poruka;
import ba.unsa.etf.chatmicroservice.repositories.PorukaRepository;
import ba.unsa.etf.chatmicroservice.requests.DodajPorukuRequest;
import ba.unsa.etf.chatmicroservice.responses.PorukaResponse;
import ba.unsa.etf.chatmicroservice.responses.PorukePosiljaocaIPrimaocaResponse;
import ba.unsa.etf.chatmicroservice.responses.Response;
import ba.unsa.etf.chatmicroservice.services.PorukaService;
import ba.unsa.etf.grpc.ActionRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class PorukaController {

    private final PorukaRepository porukaRepository;
    private final PorukaService porukaService;

    private void registerSystemEvent(
            String resurs,
            ActionRequest.TipAkcije tipAkcije,
            ActionRequest.TipOdgovoraNaAkciju tipOdgovoraNaAkciju) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8866)
                .usePlaintext()
                .build();
        ba.unsa.etf.grpc.SystemEventsServiceGrpc.SystemEventsServiceBlockingStub stub
                = ba.unsa.etf.grpc.SystemEventsServiceGrpc.newBlockingStub(channel);
        ba.unsa.etf.grpc.ActionResponse actionResponse = stub.registrujAkciju(ba.unsa.etf.grpc.ActionRequest.newBuilder()
                .setTimestampAkcije(Timestamp.from(ZonedDateTime.now(ZoneId.of("Europe/Sarajevo")).toInstant()).toString())
                .setNazivMikroservisa("chat")
                .setResurs(resurs)
                .setTipAkcije(tipAkcije)
                .setTipOdgovoraNaAkciju(tipOdgovoraNaAkciju)
                .build());
        System.out.println("Response received from server:\n" + actionResponse);
        channel.shutdown();
    }

    @GetMapping("/poruke")
    List<Poruka> all() {
        registerSystemEvent("poruka", ActionRequest.TipAkcije.GET, ActionRequest.TipOdgovoraNaAkciju.USPJEH);
        return porukaRepository.findAll();
    }

    @GetMapping("/poruke/{id}")
    public ResponseEntity<PorukaResponse> dajPoruku(@PathVariable Long id){
        PorukaResponse porukaResponse = porukaService.dajPoruku(id);
        registerSystemEvent("poruka/" + id, ActionRequest.TipAkcije.GET, ActionRequest.TipOdgovoraNaAkciju.USPJEH);
        return ResponseEntity.ok(porukaResponse);
    }

    @GetMapping("/poruke-po-posiljaocu-i-primaocu")
    public ResponseEntity<PorukePosiljaocaIPrimaocaResponse> dajPorukePoPosiljaocuIPrimaocu
            (@RequestParam Long idPosiljaoca, @RequestParam Long idPrimaoca) {
        PorukePosiljaocaIPrimaocaResponse porukePosiljaocaIPrimaocaResponse =
                porukaService.dajPorukePosiljaocaIPrimaoca(idPosiljaoca, idPrimaoca);
        registerSystemEvent("poruka?idPosiljaoca=" + idPosiljaoca + "&idPrimaoca=" + idPrimaoca,
                ActionRequest.TipAkcije.GET, ActionRequest.TipOdgovoraNaAkciju.USPJEH);
        return ResponseEntity.ok(porukePosiljaocaIPrimaocaResponse);
    }

    @PostMapping("/dodaj-poruku")
    public ResponseEntity<Response> dodajPoruku(@RequestBody DodajPorukuRequest dodajPorukuRequest) {
        Response response = porukaService.dodajPoruku(dodajPorukuRequest);
        registerSystemEvent("poruka", ActionRequest.TipAkcije.CREATE, ActionRequest.TipOdgovoraNaAkciju.USPJEH);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleNoSuchElementFoundException(ConstraintViolationException exception) {
        StringBuilder message = new StringBuilder();
        List<String> messages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        for (int i = 0; i < messages.size(); i++)
            if (i < messages.size() - 1) message.append(messages.get(i)).append("; ");
            else message.append(messages.get(i));
        return new Response(message.toString(),400);
    }
}

