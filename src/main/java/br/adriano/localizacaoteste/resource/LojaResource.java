package br.adriano.localizacaoteste.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.adriano.localizacaoteste.domain.Loja;
import br.adriano.localizacaoteste.services.LojaService;

@RestController
@RequestMapping(value = "/lojas")
public class LojaResource {
	
	@Autowired
	LojaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Loja obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<?>> findAll() {
		List<Loja> obj = service.findAll();
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = {"latitude/{lati}/longitude/{longi}"}, method = RequestMethod.GET)
	public ResponseEntity<?> findByLatitudeLongitude(@PathVariable Double lati, @PathVariable Double longi) {
		Loja obj = service.lojaMaisproxima(lati, longi);
		return ResponseEntity.ok().body(obj);
	}
}
