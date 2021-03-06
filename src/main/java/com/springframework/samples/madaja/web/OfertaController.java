package com.springframework.samples.madaja.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springframework.samples.madaja.model.Oferta;
import com.springframework.samples.madaja.model.Vehiculos;
import com.springframework.samples.madaja.service.OfertaService;
import com.springframework.samples.madaja.service.VehiculosService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class OfertaController {
	
	private static final String VIEW_OFERTA_CREATE_FORM = "oferta/createOfertaForm";
	private static final String VIEW_OFERTA_UPDATE_FORM = "oferta/updateOfertaForm";
	private static final String VIEW_REDIRECT="redirect:/oferta";
	
	private final OfertaService ofertaService;
	private final VehiculosService vehiculosService;
	
	@Autowired
	public OfertaController(OfertaService ofertaService, VehiculosService vehiculosService) {
		this.ofertaService=ofertaService;
		this.vehiculosService =vehiculosService;
	}
	
	//PAGINACIÓN
	@GetMapping(value = { "/oferta" })
    public String findAll(@RequestParam Map<String, Object> params, ModelMap model){

        int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1 ) : 0;

        PageRequest pageRequest = PageRequest.of(page, 10);

        Page<Oferta> pageOferta = this.ofertaService.getAll(pageRequest);

        int totalPage = pageOferta.getTotalPages();
        if(totalPage > 0) {
            // lista con todas las páginas que hay:
            List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }

        model.addAttribute("ofertas", pageOferta.getContent());
        model.addAttribute("current", page+1);
        model.addAttribute("next", page+2);
        model.addAttribute("prev", page);
        model.addAttribute("max", totalPage);

        return "oferta/mostrarOfertas";
    }
	
	//-------------------------------------API--------------------------------
	@GetMapping(value = {"/ofertaAPI"})
	public String showOfertasListAPI() {
		return "oferta/mostrarOfertasAPI";
	}
	//-------------------------------------API--------------------------------
	
	@GetMapping(value = {"/oferta/{ofertaId}"})
	public String showOferta(@PathVariable("ofertaId") int ofertaId, ModelMap map) {
		Oferta oferta = this.ofertaService.findOfertaById(ofertaId);
		List<Vehiculos> vehiculos = vehiculosService.findByOferta(ofertaId).stream().collect(Collectors.toList());
		map.put("oferta", oferta);
		map.put("vehiculos", vehiculos);
		log.info("Se ha realizado una consulta a la oferta con id: " + ofertaId);
		return "oferta/ofertaDetails";
	}

	//-------------------------------------API--------------------------------
	@GetMapping(value = {"/ofertaAPI/{ofertaId}"})
	public String showOfertaAPI(@PathVariable("ofertaId") int ofertaId, ModelMap map) {
		map.put("id", ofertaId);
		return "oferta/ofertaDetailsAPI";
	}
	//-------------------------------------API--------------------------------

	@InitBinder("oferta")
	public void initVehiculoBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@InitBinder
	public void initBinder(final WebDataBinder binder){
	  final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd"); 
	  binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@InitBinder
	public void setValidator(WebDataBinder dataBinder) {
		dataBinder.setValidator(new OfertaValidator());
	}
	
	
	@ModelAttribute("vehiculosDisponibles")
	public List<Vehiculos> matriculasDisponible(){
		return vehiculosService.findAllVehiculosDisponiblesYsinOfertas().stream().collect(Collectors.toList());
	}
	
	@GetMapping(value = "/oferta/new")
	public String creationFormOferta(ModelMap model) {
		Oferta oferta = new Oferta();
		model.put("oferta", oferta);
		return VIEW_OFERTA_CREATE_FORM;
	}
	//-------------------------------------API--------------------------------
	@GetMapping(value = "/ofertaAPI/new")
	public String creationOfertaAPI() {
		return "oferta/createOfertaFormAPI";
	}
	//-------------------------------------API--------------------------------
	@PostMapping(value = "/oferta/new")
	public String processFormOferta(@Valid Oferta oferta,BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.put("oferta", oferta);
			return VIEW_OFERTA_CREATE_FORM;
		}
		else {
				this.ofertaService.saveOferta(oferta);
				List<Vehiculos> vehiculos = oferta.getVehiculos().stream().collect(Collectors.toList());
				for(Vehiculos vehiculo: vehiculos) {
					vehiculo.setOferta(oferta);
					vehiculosService.saveVehiculo(vehiculo);
				}
			return VIEW_REDIRECT;
		}
	}

	@GetMapping(value = "/oferta/{ofertaId}/edit")
	public String updateFormOferta(@PathVariable("ofertaId") int ofertaId, ModelMap model) {
		Oferta oferta = this.ofertaService.findOfertaById(ofertaId);
		List<Vehiculos> vehiculos = oferta.getVehiculos().stream().collect(Collectors.toList());
		List<Vehiculos> vehiculosDisponibles = vehiculosService.findAllVehiculosDisponiblesYsinOfertas().stream().collect(Collectors.toList());
		vehiculos.addAll(vehiculosDisponibles);
		model.put("vehiculos",vehiculos);
		model.put("oferta", oferta);
		return VIEW_OFERTA_UPDATE_FORM;
	}
	
	@PostMapping(value = "/oferta/{ofertaId}/edit")
	public String processUpdateFormOferta(@PathVariable("ofertaId") int ofertaId, ModelMap model, @Valid Oferta oferta, BindingResult result) {
		if(result.hasErrors()) {
			model.put("oferta", oferta);
			List<Vehiculos> vehiculos = vehiculosService.findByOferta(ofertaId).stream().collect(Collectors.toList());
			List<Vehiculos> vehiculosDisponibles = vehiculosService.findAllVehiculosDisponiblesYsinOfertas().stream().collect(Collectors.toList());
			vehiculos.addAll(vehiculosDisponibles);
			model.put("vehiculos", vehiculos);
			return VIEW_OFERTA_UPDATE_FORM;
		}else {
			//Quitar todos los vehiculos que han dejado de aplicarse la oferta
			if(!oferta.getVehiculos().isEmpty()) {
				List<Vehiculos> vehiculosOld = vehiculosService.findByOferta(ofertaId).stream().collect(Collectors.toList());
				for(Vehiculos vehiculo: vehiculosOld) {
					vehiculo.setOferta(null);
				}
			}
			//Guardar la oferta actualizada
			Oferta ofertaUpdate = this.ofertaService.findOfertaById(ofertaId);
			BeanUtils.copyProperties(oferta, ofertaUpdate,"id");
			this.ofertaService.saveOferta(ofertaUpdate);
			List<Vehiculos> vehiculos = ofertaUpdate.getVehiculos().stream().collect(Collectors.toList());
			for(Vehiculos vehiculo: vehiculos) {
				vehiculo.setOferta(ofertaUpdate);
				vehiculosService.saveVehiculo(vehiculo);
			}
			return VIEW_REDIRECT;
		}
	}
	//-------------------------------------API--------------------------------
	@GetMapping(value = "/ofertaAPI/{ofertaId}/edit")
	public String updateOfertaAPI(@PathVariable("ofertaId") int ofertaId, ModelMap map) {
		map.put("id", ofertaId);
		return "oferta/updateOfertaAPI";
	}
	
	//-------------------------------------API--------------------------------
	
	@GetMapping(value = "/oferta/{ofertaId}/delete")
	public String deleteOferta(@PathVariable("ofertaId") int ofertaId, ModelMap map) {
		Oferta oferta = this.ofertaService.findOfertaById(ofertaId);
		if(!oferta.getVehiculos().isEmpty()) {
			List<Vehiculos> vehiculosOld = vehiculosService.findByOferta(ofertaId).stream().collect(Collectors.toList());
			for(Vehiculos vehiculo: vehiculosOld) {
				vehiculo.setOferta(null);
			}
		}
		this.ofertaService.deleteById(ofertaId);
		return VIEW_REDIRECT;
	}
	
	//-------------------------------------API--------------------------------
	@GetMapping(value = "/ofertaAPI/{ofertaId}/delete")
	public String deleteOfertaAPI(@PathVariable("ofertaId") int ofertaId, ModelMap map) {
		map.put("id", ofertaId);
		return "oferta/mostrarOfertasAPI";
	}
	//-------------------------------------API--------------------------------
}
