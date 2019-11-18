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
	
	private static double distance(double lat1, double lat2,double lon1,  double lon2) {
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
	
//	public double distance(double lat1, double lat2, double lon1,
//	        double lon2) {
//
//		double el1=2.0;
//		double el2=2.0;
//	    final int R = 6371; // Radius of the earth
//
//	    double latDistance = Math.toRadians(lat2 - lat1);
//	    double lonDistance = Math.toRadians(lon2 - lon1);
//	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
//	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//	    double distance = R * c * 1000; // convert to meters
//
//	    double height = el1 - el2;
//
//	    distance = Math.pow(distance, 2) + Math.pow(height, 2);
//
//	    return Math.sqrt(distance);
//	}
//		
//
	
//	private Double distance(Double lat1, Double lat2, Double lon1, Double lon2) {
//	    final int R = 6371; // Radius of the earth
//
//	    Double latDistance = deg2rad(lat2 - lat1);
//	    Double lonDistance = deg2rad(lon2 - lon1);
//	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//	            + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
//	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//	    double distance = R * c * 1000; // convert to meters
//	    
//	    distance = Math.pow(distance, 2);	
//	    return Math.sqrt(distance);
//	}

//	public double distance(double latitude, double latitudePto, double longitude, double longitudePto){ 
//		double dlon, dlat, a, distancia; 
//		dlon = longitudePto - longitude; 
//		dlat = latitudePto - latitude; 
//		a = Math.pow(Math.sin(dlat/2),2) + Math.cos(latitude) * Math.cos(latitudePto) * Math.pow(Math.sin(dlon/2),2); 
//		distancia = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
//		return distancia; 
//			/* 6378140 is the radius of the Earth in meters*/ 
//	}	
	
		
//	private double deg2rad(double deg) {
//	    return (deg * Math.PI / 180.0);
//	}
	
//	private double distance(Double lat1, Double lat2, Double lon1, Double lon2,
//	        Double el1, Double el2) {
//
//		
//		
//	    final int R = 6371; // Radius of the earth
//
//	    Double latDistance = deg2rad(lat2 - lat1);
//	    Double lonDistance = deg2rad(lon2 - lon1);
//	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//	            + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
//	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//	    double distance = R * c * 1000; // convert to meters
//
//	    double height = el1 - el2;
//	    distance = Math.pow(distance, 2) + Math.pow(height, 2);
//	    return Math.sqrt(distance);
//	}
}
