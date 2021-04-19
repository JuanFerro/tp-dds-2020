package OperacionDeEgresos;

import Excepciones.InvalidConditionException;
import Organizaciones.Entidad;
import Organizaciones.RepositorioEntidad;
import Otros.PersistedEntity;
import Usuario.Usuario;
import Verificador.OperacionDeEgresos.CondicionCantidadRequeridaPresupuestos;
import Verificador.OperacionDeEgresos.CondicionCriterioSeleccion;
import Verificador.OperacionDeEgresos.CondicionEgresoBasadoPresupuesto;
import Verificador.Verificador;
import Usuario.MensajeValidacionEgreso;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Egreso extends PersistedEntity {

	public Egreso() {

	}

	@ManyToOne
	@JoinColumn(name = "proveedor_id")
	private Proveedor proveedor;

	private java.sql.Date fecha;
	private Integer totalOperacion;


	@ManyToOne
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
	@JoinColumn(name = "medio_de_pago_id")
	private MedioDePago medioDePago;

	@Column(nullable = true)
	@Embedded
	private DocumentoComercial documentoComercial;
	@OneToMany
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
	@JoinColumn(name = "egreso_id")
	private List<Item> listaItems;

	@OneToMany
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
	@JoinColumn(name = "egreso_id")
	private List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
	static private Integer presupuestosNecesarios = 2; // No sabemos si tiene que guardarse en una tabla de parametría para poder editarlo desde la base o no hace falta
	@Enumerated
	private CriterioSeleccionProveedor criterioSeleccion;
	@Enumerated
	private EstadoEgreso estado;
	@ManyToMany
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
	private List<Usuario> revisoresDeCompra = new ArrayList<Usuario>();
	@ManyToMany
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)
	private List<Etiqueta> etiquetas = new ArrayList<>();

	public EstadoEgreso getEstado(){return estado;}

    public void setEstado(EstadoEgreso nuevoEstado){ estado = nuevoEstado; }


	public Egreso(Proveedor proveedor, Integer totalOperacion, MedioDePago medioDePago, List<Item> listaItems, java.time.LocalDate fecha, CriterioSeleccionProveedor criterioSeleccion) {
		this.proveedor = proveedor;
		this.fecha = Date.valueOf(fecha);
		this.totalOperacion = totalOperacion;
		this.medioDePago = medioDePago;
		this.listaItems = listaItems;
		this.criterioSeleccion = criterioSeleccion;

	}

	public void setDocumentoComercial(DocumentoComercial documentoComercial){
		this.documentoComercial = documentoComercial;
	}

	private Verificador verificador () {
			Verificador verificador = new Verificador();
			if (noNecesitaValidacion())
				return verificador;

			verificador.agregarCondicion(new CondicionCantidadRequeridaPresupuestos());
			verificador.agregarCondicion(new CondicionEgresoBasadoPresupuesto());
			verificador.agregarCondicion(new CondicionCriterioSeleccion());

			return verificador;
	}

	public boolean noNecesitaValidacion() {
		return presupuestosNecesarios.equals(0);
	}

    public void validar () {
        try {
            this.verificador().verificarCondiciones(this);
            ResultadoValidacion resultado = new ResultadoValidacion();
            resultado.valido(this);
        }
        catch (InvalidConditionException ex) {
            ResultadoValidacion resultado = new ResultadoValidacion();
            resultado.invalido(this, ex);
        }
    }

	public void notificarALosRevisores(MensajeValidacionEgreso message) {
		revisoresDeCompra.forEach(usr -> usr.notificarValidacion(message));
	}

	public void agregarPresupuestos(Presupuesto unPresupuesto){
		presupuestos.add(unPresupuesto);
	}

	public boolean estaValidado(){
        return estado == EstadoEgreso.VALIDO;
    }
	public List<Item> getItems(){
		return listaItems;
	}
	public void agregarItem (Item item) {
		totalOperacion += (int)item.getValor();
		listaItems.add(item);
	}

	public void eliminarItem (Item item){
		totalOperacion -= (int)item.getValor();
		listaItems.remove(item);
	}

	public List<Presupuesto> presupuestos() {
		return presupuestos;
	}
	public int getPresupuestosNecesarios(){ return presupuestosNecesarios;}
	public Integer getTotalOperacion(){return totalOperacion;}
	public CriterioSeleccionProveedor getCriterioSeleccion(){ return criterioSeleccion;}
	public String tipoMoneda() { return proveedor.monedaSegunElPais(); }
	public java.sql.Date getFecha(){
		return fecha;
	}

	public MedioDePago getMedioDePago() {
		return medioDePago;
	}

	public String getFechaFormateada() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(fecha);
	}
	public Proveedor getProveedor() { return proveedor; }
	public DocumentoComercial getDocumentoComercial () { return documentoComercial; }

	public void darDeAltaRevisorDeCompra(Usuario usuario) {
		if (!revisoresDeCompra.contains(usuario))
			revisoresDeCompra.add(usuario);
	}

	public boolean elEgresoTieneElItem(Item item){
		return getItems().stream().anyMatch(it -> it.getNombre().equals(item.getNombre()));
	}
	public boolean elEgresoTieneElPresupuesto(Presupuesto presupuesto){
		return presupuestos().contains(presupuesto);
	}

	// ETIQUETAS
	public List<Etiqueta> etiquetas() { return etiquetas; }
	public void agregarEtiqueta(Etiqueta etiqueta) { etiquetas.add(etiqueta); }
	public void eliminarEtiqueta(Etiqueta etiqueta) { etiquetas.remove(etiqueta); }
	public boolean tieneEtiqueta(Etiqueta etiqueta) { return etiquetas.contains(etiqueta); }

	public boolean elEgresoContieneLaEtiqueta(Etiqueta etiqueta) {
		return etiquetas.contains(etiqueta);
	}

	public Entidad getEntidad() {
		return RepositorioEntidad.instancia.entidadQueTieneAlEgreso(this);
	}
	
}
