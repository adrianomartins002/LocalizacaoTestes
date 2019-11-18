package br.adriano.localizacaoteste.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.adriano.localizacaoteste.domain.Loja;
import br.adriano.localizacaoteste.repository.LojaRepository;
import br.adriano.localizacaoteste.services.exceptions.ObjectNotFoundException;


@Service
public class LojaService {

	@Autowired
	LojaRepository repository;
	
	public Loja find(Integer id) {
		Optional<Loja> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Loja.class.getName()));
	}
	
	public List<Loja> findAll(){
		return repository.findAll();
	}
	
	public Loja lojaMaisproxima(double lati, double longi) {
		List<Loja> lojas = this.findAll();
		Loja lojaMaisProxima = new Loja();
		
		Map<Integer, Double> distanciaFilial = new HashMap<Integer, Double>();
		Integer ultimaLoja = 0;
		
		for (Loja loja : lojas) {
			if(distanciaFilial.isEmpty()) {
				distanciaFilial.put(loja.getId(), this.distance(lati, loja.getLatitude(), longi, loja.getLongitude()));
				ultimaLoja = loja.getId();
				continue;
			}
			double distanciaAgora = this.distance(lati, loja.getLatitude(), longi, loja.getLongitude());
			double distanciaSalva =distanciaFilial.get(ultimaLoja); 
			if(distanciaAgora <  distanciaSalva) {
				distanciaFilial.clear();
				distanciaFilial.put(loja.getId(), distanciaAgora);
				ultimaLoja = loja.getId();
			}
			
		}
		
		for (Integer dist : distanciaFilial.keySet()) {
			lojaMaisProxima = this.find(dist);	
			break;
		}
		
		return lojaMaisProxima;
	}
	
	private double distance(double lat1, double lat2,double lon1,  double lon2) {
		String unit = "K";
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit == "K") {
				dist = dist * 1.609344;
			} else if (unit == "N") {
				dist = dist * 0.8684;
			}
			return (dist);
		}
	}
	
}
