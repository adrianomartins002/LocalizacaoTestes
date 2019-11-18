package br.adriano.localizacaoteste;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.adriano.localizacaoteste.domain.Loja;
import br.adriano.localizacaoteste.repository.LojaRepository;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	@Autowired
	LojaRepository lojaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	
	@Override
	public void run(String... args) throws Exception {

		double lat1 = -2.515749;
		double long1 = -44.245237;
		Loja loja1 = new Loja(null, "Cohama", lat1, long1);
		
		double lat2 = -2.517986;
		double longi2 = -44.257688;
		Loja loja2 = new Loja(null, "Mix curva do 90", lat2, longi2);
		
		double lat3 = -2.527028;
		double longi3 = -44.255053;
		Loja loja3 = new Loja(null, "Shopping da ilha", lat3, longi3);
		
		lojaRepository.saveAll(Arrays.asList(loja1, loja2, loja3));
	}
}
