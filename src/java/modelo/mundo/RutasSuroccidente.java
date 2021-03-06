package modelo.mundo;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.datos.ClienteDAO;
import modelo.datos.MarcaDAO;


/**
 * clase principal del programa esta clase se encarga del manjo de los  requerimientos y la informacion del hotel
 * @author megasoft 
 */
public class RutasSuroccidente {
        
    
        /**
         * constante para el manejo de horas en el sistema y la base de datos
         */
        public final static String FORMATO_HORAS="hh:mm:ss";
    
	
	/**
	 * atributo encargado del manejo de las marcas en la base de datos
	 */
	private MarcaDAO marcaDAO;
	
	
	/**
	 * la lista de marcas
	 */
	private ArrayList<Marca> marcas;
	
        
        /**
	 * cliente de un tiquete
	 */
	private ArrayList<Cliente> clientes;


	/**
	 * atributo encargado de los clientes en la base de datos
	 */
	private ClienteDAO clienteDAO;
	
        
        /**
         * atributo parte del patron singleton
         */
        private static RutasSuroccidente instancia;
        
        
        /**
         * contador de tiquetes
         */
        private int numeroTiquetes;
        
        
        //-------------------------------------------------------------------------------------------
	//Constructor
	//-------------------------------------------------------------------------------------------
	
        
        /**
         * metodo que da la instancia del mundo
         * @return la instancia del mundo del problema
         */
	public static RutasSuroccidente darInstancia(){
            if(instancia==null){
                instancia= new RutasSuroccidente();
            }
            return instancia;
        }
        
	/**
	 * metodo constructor de la calse RutasSuroccidente
	 * <b>post:</b> se ha construido una intancia de la clase RutasSuroccidente
	 */
	private RutasSuroccidente() {
		marcaDAO= new MarcaDAO();
		marcas= new ArrayList<Marca>();
                clientes= new ArrayList<Cliente>();
                clienteDAO= new ClienteDAO();
                //cargar();
		// TODO Auto-generated constructor stub
	}

        
        //----------------------------------------------------------------------
        //GETTERS AND SETTERS
        //----------------------------------------------------------------------
        
        
        /**
         * metofo que permite obterner el objeto para guardar una marca
         * @return el objeto para obtener un marca
         */
        public MarcaDAO getMarcaDAO() {
            return marcaDAO;
        }

        
        /**
         * metodo que establece el objet para guardar marcas
         * @param marcaDAO el objeto a establecer
         */
        public void setMarcaDAO(MarcaDAO marcaDAO) {
            this.marcaDAO = marcaDAO;
        }

        
        /**
         * metodo que permite obtener la lista de marcas
         * @return el objeto de la lista de marcas
         */
        public ArrayList<Marca> getMarcas() {
            return marcas;
        }

        
        /**
         * metodo que permite establecer la lista de marcas
         * @param marcas la lista de marcas a establecer
         */
        public void setMarcas(ArrayList<Marca> marcas) {
            this.marcas = marcas;
        }
        
        
        /**
	 * metodo que permite retornar el clienteDAO
	 * @return el cliente DAO
	 */
	public ClienteDAO getClienteDAO() {
	 	return clienteDAO;
	}
	
	
	/**
	 * metodo que permite cambiar el atributo clienteDAO<br>
	 *<b>post:</b> se ha cambiado el atributo DAO por el pasado como parametro
	 * @param clienteDAO atributo DAO clienteDAO!=""
	 */
	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

        
        /**
         * retorna la lista de clientes
         * @return  la lista de clientes
         */
        public ArrayList<Cliente> getClientes() {
            return clientes;
        }

        /**
         * cambia la lista de clientes  por la pasada como parametro
         * @param clientes la lista de clientes pasada como parametro
         */
        public void setClientes(ArrayList<Cliente> clientes) {
            this.clientes = clientes;
        }   
        
        
	//-------------------------------------------------------------------------------------------
	//REQUERIMIENTOS
	//-------------------------------------------------------------------------------------------	
	
        
        /**
         * metodo que permite cargar el estado del mundo en la base de datos<br/>
         * <b>post:</b> se ha cargado el estado del mundo de la base de datos
         */
        public void cargar(){
            clientes= clienteDAO.seleccionar();
            marcas=marcaDAO.seleccionar();
            for(int i=0; i<marcas.size();i++){
                Marca miMarca = marcas.get(i);
                ArrayList<Linea> Lineas = miMarca.getLineaDAO().seleccionar(miMarca);
                miMarca.setLineas(Lineas);
                ArrayList<Linea> misLineas = miMarca.getLineas();
                for(int j=0; j<misLineas.size(); j++){
                    Linea miLinea = misLineas.get(j);
                    ArrayList<Vehiculo> vehiculos = miLinea.getVehiculoDAO().seleccionar(miMarca, miLinea);
                    miLinea.setVehiculos(vehiculos);
                    ArrayList<Vehiculo> misVehiculos = miLinea.getVehiculos();
                    for(int k=0; k<misVehiculos.size();k++){
                        Vehiculo miVehiculo = misVehiculos.get(k);
                        Propietario miPropietario = miVehiculo.getPropietarioDAO().seleccionar(miVehiculo);
                        miVehiculo.setPropietario(miPropietario);
                    }
                }
            }
        }
	//-------------------------------------------------------------------------------------------
	//GESTIONAR MARCA
	//-------------------------------------------------------------------------------------------
	
	
	/**
	 * metodo que permite buscar una marca por nombre<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas
	 * <b>post:</b> se  ha buscado y retornado la marca con el nombre pasado como parametro
	 * @param nNombre el nombre con el que se busca la marca nNombre !=null
	 * @return la marca con en nombre si no se encuentra una marca con el nombre como parametro null
	 */
	public Marca buscarMarca(String nNombre){
            Marca retorno= null;
            for(int i=0; i<marcas.size();i++){
                String miNombre= marcas.get(i).getNombre();
                if(miNombre.equals(nNombre)){
                    retorno= marcas.get(i);
                }
            }
            return retorno;
	}
	
	
	/**
	 * metodo que permite eliminar una marca<br>
	 * <b>pre:</b> se ha inicializado la lista de marcas y el atributo marcasDAO<br>
	 * <b>post:</b> se ha eliminado una marca con el nombre pasado como parametro
	 * @param nNombre el nombre por el cual se elimina la marca
	 */
	public void eliminarMarca(String nNombre){
            Marca buscada= buscarMarca(nNombre);
            marcas.remove(buscada);
            marcaDAO.eliminar(buscada);
	}
	
	
	/**
	 * metodo que permite modificar una marca<br>
	 * <b>pre:</b> se ha inicializado la lista de marcas y el atributo marcasDAO<br>
	 * <b>post:</b> se ha modificado la marca con el nombre pasado como paramtro
	 * @param nNombre el nuevo nombre de la marca
         * @param vNombre el viejo nombre de la marca
	 */
	public void modificarMarca(String nNombre, String vNombre){
            Marca buscada= buscarMarca(vNombre);
            buscada.setNombre(nNombre);
            marcaDAO.actualizar(buscada,vNombre);
	}
	
	
	/**
	 * metodo que permite agregar una marca
	 * <b>pre:</b> se ha inicializado la lista de marcas y el atributo marcasDAO<br>
	 * <b>post:</b> se ha agregao una marca con el nombre pasado como parametro
	 * @param nNombre el nombre de la marca
	 */
	public void agregarMarca(String nNombre){
            Marca buscada= buscarMarca(nNombre);
            if(buscada==null){
                Marca agregar= new Marca(nNombre);
                marcas.add(agregar);
                marcaDAO.agregar(agregar);
            }
	}
	
	
	//-------------------------------------------------------------------------------------------
	//GESTIONAR LINEA
	//-------------------------------------------------------------------------------------------
	
	
	/**
	 * metodo que permite buscar una linea por nombre<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas y lineas<br>
	 * <b>post:</b> se  ha buscado y retornado la linea con el nombre pasado como parametro
	 * @param nNombre el nombre con el que se busca la linea nNombre !=null
	 * @return la linea con en nombre si no se encuentra una linea con el nombre como parametro null
	 */
	public Linea buscarLinea(String nNombre){
            Linea retorno=null;
		for(int i=0; i<marcas.size();i++){
                    ArrayList<Linea> lineas = marcas.get(i).getLineas();
                    for(int j=0; j<lineas.size(); j++){
                        Linea linea= lineas.get(j);
                        if(linea.getNombre().equals(nNombre)){
                            retorno=linea;
                        }
                    }
                }
            return retorno;
	}
	
	
	/**
	 * metodo que permite eliminar una linea<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas
	 * <b>post:</b> se ha eliminado una marca con el nombre pasado como parametro del programa y la base de datos
	 * @param nNombre el nombre por el cual se elimina la linea
	 */
	public void eliminarLinea(String nNombre){
            Marca marcaE=null;
            Linea buscada = buscarLinea(nNombre);
            for(int i=0;i<marcas.size();i++){
                Marca marca= marcas.get(i);
                for(int j=0;j<marca.getLineas().size();j++){
                    Linea linea= marca.getLineas().get(j);
                    if(linea.getNombre().equals(nNombre)){
                        marcaE=marca;
                    }
                }
            }
            if(marcaE!=null){
                marcaE.getLineas().remove(buscada);
                //marcaE.getLineaDAO().eliminar(marcaE, buscada);
            }
	}
	
	
	/**
	 * metodo que permite modificar una linea<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas
	 * <b>post:</b> se ha modificado la linea con el nombre pasado como parametro
	 * @param nNombre el nuevo nombre de la linea
         * @param vNombre el viejo nombre de la linea
	 */
	public void modificarLinea(String nNombre, String vNombre)
	{
        Marca marcaE=null;
        Linea buscada = buscarLinea(vNombre);
        for(int i=0;i<marcas.size();i++)
		{
            Marca marca= marcas.get(i);
            for(int j=0;j<marca.getLineas().size();j++)
			{
                Linea linea= marca.getLineas().get(j);
                if(linea.getNombre().equals(vNombre))
				{
                    marcaE=marca;
                }
            }
        }
            if(marcaE!=null){
                buscada.setNombre(nNombre);
                //marcaE.getLineaDAO().actualizar(marcaE, buscada, vNombre);
            }
	}
	
	
	/**
	 * metodo que permite agregar una linea
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas
	 * <b>post:</b> se ha agregao una linea con el nombre pasado como parametro
	 * @param nNombreLinea  el nombre de la linea
         * @param nNombreMarca el nombre de la marca
	 */
	public void agregarLinea(String nNombreMarca, String nNombreLinea){
            Marca buscada= buscarMarca(nNombreMarca);
            Linea lineaB= buscarLinea(nNombreLinea);
            if(lineaB==null)
            {
                Linea nueva= new Linea(nNombreLinea);
                buscada.getLineas().add(nueva);
                //buscada.getLineaDAO().agregar(buscada, nueva);
            }
	}
	
	
	//------------------------------------------------------------------------------------------
	//GESRIONAR VEHICULO
	//------------------------------------------------------------------------------------------
	
	
	/**
	 * metodo que permite buscar un vehiculo por placa<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas y vehiculos<br>
	 * <b>post:</b> se  ha buscado y retornado el vehiculo con la placa pasada como parametro
	 * @param nPlaca la placa con la que se busca el vehiculo nPlaca !=null
	 * @return el vehiculo con la placa pasada como parametro de lo contrario null
	 */
	public Vehiculo buscarVehiculo(String nPlaca){
            Vehiculo retorno=null;
            for(int i=0;i<marcas.size();i++){
                Marca miMarca = marcas.get(i);
                ArrayList<Linea> misLineas = miMarca.getLineas();
                for(int j=0;j<misLineas.size();j++){
                    Linea miLinea = misLineas.get(j);
                    ArrayList<Vehiculo> misVehiculos = miLinea.getVehiculos();
                    for(int k=0;k<misVehiculos.size();k++){
                        Vehiculo miVehiculo = misVehiculos.get(k);
                        if(miVehiculo.getPlaca().equals(nPlaca)){
                            retorno= miVehiculo;
                        }
                    }
                }
            }
            return retorno;
	}
	
	
	/**
	 * metodo que permite eliminar una linea<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas y vehiculos<br>
	 * <b>post:</b> se ha eliminado un vehiculo con la placa pasada como parametro del programa y la base de datos
	 * @param nPlaca la placa con la cual se elimina el vehiculo
	 */
	public void eliminarVehiculo(String nPlaca){
            for(int i=0;i<marcas.size();i++){
                Marca miMarca = marcas.get(i);
                ArrayList<Linea> misLineas = miMarca.getLineas();
                for(int j=0;j<misLineas.size();j++){
                    Linea miLinea = misLineas.get(j);
                    ArrayList<Vehiculo> misVehiculos = miLinea.getVehiculos();
                    for(int k=0;k<misVehiculos.size();k++){
                        Vehiculo miVehiculo = misVehiculos.get(k);
                        if(miVehiculo.getPlaca().equals(nPlaca)){
                            misVehiculos.remove(miVehiculo);
                            //miLinea.getVehiculoDAO().eliminar(miMarca, miLinea, miVehiculo);
                        }
                    }
                }
            }
	}
	
	
	/**
	 * metodo que permite modificar un vehiculo
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas y vehiculos<br>
	 * <b>post:</b> se ha modificado los datos del vehiculo por los pasados como parametro
	 * @param nModelo el modelo del vehiculo nModelo>0
	 * @param nNumeroPasajeros el numero de pasajeros del vehiculos nNumeroPasajeros>0 
	 * @param nFotografia la fotografia del vehiculo nFotografia !=null
	 */
	public void modificarVehiculo(int nModelo, int nNumeroPasajeros, BufferedImage nFotografia, String nPlaca){
            for(int i=0;i<marcas.size();i++){
                Marca miMarca = marcas.get(i);
                ArrayList<Linea> misLineas = miMarca.getLineas();
                for(int j=0;j<misLineas.size();j++){
                    Linea miLinea = misLineas.get(j);
                    ArrayList<Vehiculo> misVehiculos = miLinea.getVehiculos();
                    for(int k=0;k<misVehiculos.size();k++){
                        Vehiculo miVehiculo = misVehiculos.get(k);
                        if(miVehiculo.getPlaca().equals(nPlaca)){
                            miVehiculo.setFotografia(nFotografia);
                            miVehiculo.setModelo(nModelo);
                            miVehiculo.setNumeroPasajeros(nNumeroPasajeros);    
                            //miLinea.getVehiculoDAO().actualizar(miMarca, miLinea, miVehiculo);
                        }
                    }
                }
            }
	}
	
	
	/**
	 * metodo que permite agregar un vehiculo
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas y vehiculos<br>
	 * <b>post:</b> se ha agregado un vehiculo con los datos pasados como parametros<br/>
	 * @param nLinea la linea del vehucilo nLinea!=""
	 * @param nModelo el modelo del vehiculo nModelo>0
	 * @param nPlaca la placa del vehiculo nPlaca!=""
	 * @param nNumeroPasajeros el numero de pasajeros del vehiculos nNumeroPasajeros>0 
	 * @param nFotografia la fotografia del vehiculo nFotografia !=null
	 */
	public void agregarVehiculo(String nLinea, int nModelo, String nPlaca, int nNumeroPasajeros, BufferedImage nFotografia){
            Vehiculo buscado = buscarVehiculo(nPlaca);
            if(buscado==null){
                for(int i=0; i< marcas.size(); i++){
                    Marca miMarca = marcas.get(i);
                    ArrayList<Linea> misLineas = miMarca.getLineas();
                    for(int j=0; j<misLineas.size();j++){
                        Linea miLinea = misLineas.get(j);
                        if(miLinea.getNombre().equals(nLinea)){
                            Vehiculo v= new Vehiculo(nModelo, nPlaca, nNumeroPasajeros, nFotografia);
                            miLinea.getVehiculos().add(v);
                            //miLinea.getVehiculoDAO().agregar(miMarca, miLinea, v);
                        }
                    }
                }
            }
	}
	
	
	//------------------------------------------------------------------------------------------
	//GESTIONAR PROPIETARIO
	//------------------------------------------------------------------------------------------
	
	
	/**
	 * metodo que permite buscar un propietario por identificacion<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas, vehiculos<br>
	 * <b>post:</b> se  ha buscado y retornado el propietario con la identificacion pasada como paramtro
	 * @param nIdentificacion la identificacion con la que se busca el propietario nIdentificacion!=null
         * @param nPlaca la placa del vehiculo
	 * @return el propietario la identificacion pasada como parametro de lo contrario null
	 */
	public Propietario buscarPropietario(int nIdentificacion){
            Propietario retorno=null;
            for(int i=0;i<marcas.size();i++){
                Marca miMarca = marcas.get(i);
                ArrayList<Linea> misLineas = miMarca.getLineas();
                for(int j=0;j<misLineas.size();j++){
                    Linea miLinea = misLineas.get(j);
                    ArrayList<Vehiculo> misVehiculos = miLinea.getVehiculos();
                    for(int k=0;k<misVehiculos.size();k++){
                        Vehiculo miVehiculo = misVehiculos.get(k);
                        Propietario miPropietario = miVehiculo.getPropietario();
                        if(miPropietario!=null){
                            if(miPropietario.getIdentificacion()==nIdentificacion){
                                retorno= miPropietario;
                            }
                        }
                    }
                }
            }
            return retorno;
	}
	
	
	/**
	 * metodo que permite eliminar una propietario de un vehiculo<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas, vehiculos<br>
	 * <b>post:</b> se ha eliminado el propitario del vehiculo con la palca pasada como parametro del programa y la base de datos
	 * @param nPlaca la placa con el cual se elimina e vehiculo
         * @param nIdentificacion la identificacion con la que se busca el propietario nIdentificacion!=null
	 * @throws Exception - El cliente no existe.
	 */
	public void eliminarPorpietarioVehiculo(int nIdentificacion)
	{
            for(int i=0;i<marcas.size();i++){
                Marca miMarca = marcas.get(i);
                ArrayList<Linea> misLineas = miMarca.getLineas();
                for(int j=0;j<misLineas.size();j++){
                    Linea miLinea = misLineas.get(j);
                    ArrayList<Vehiculo> misVehiculos = miLinea.getVehiculos();
                    for(int k=0;k<misVehiculos.size();k++){
                        Vehiculo miVehiculo = misVehiculos.get(k);
                        Propietario miPropietario = miVehiculo.getPropietario();
                        if(miPropietario!=null){
                            if(miPropietario.getIdentificacion()==nIdentificacion){
                                miVehiculo.getPropietarioDAO().eliminar(miVehiculo, miPropietario);
                                miVehiculo.setPropietario(null);
                            }   
                        }
                    }
                }
            }
	}
	
	
	/**
	 * metodo que permite modificar un prietario<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas, vehiculos<br>
	 * <b>post:</b> se ha modificado los datos del propuetario del vehiculo 
	 * @param nAplellidos los apellidos del propuetario nApellidos!=""
	 * @param nDireccion la direccion del propietario nDireccion!=""
	 * @param nIdentificacion la identificacion del propietario nIdentificacion>0
	 * @param nNombres los nombres del propietario nNombres!=""
	 * @param nTelefono el telefono del propietario telefono>0
	 * @throws Exception si no existe el propietario con la identificacion ingresada
	 */
	public void modificarPropietario(String nAplellidos, String nDireccion, int nIdentificacion, String nNombres, int nTelefono)
	{
            for(int i=0;i<marcas.size();i++){
                Marca miMarca = marcas.get(i);
                ArrayList<Linea> misLineas = miMarca.getLineas();
                for(int j=0;j<misLineas.size();j++){
                    Linea miLinea = misLineas.get(j);
                    ArrayList<Vehiculo> misVehiculos = miLinea.getVehiculos();
                    for(int k=0;k<misVehiculos.size();k++){
                        Vehiculo miVehiculo = misVehiculos.get(k);
                        Propietario miPropietario = miVehiculo.getPropietario();
                        if(miPropietario!=null){
                            if(miPropietario.getIdentificacion()==nIdentificacion){
                                miPropietario.setApellidos(nAplellidos);
                                miPropietario.setDireccion(nDireccion);
                                miPropietario.setNombres(nNombres);
                                miPropietario.setTelefono(nTelefono);
                                miVehiculo.getPropietarioDAO().actualizar(miVehiculo, miPropietario);
                            }
                        }
                    }
                }
            }
	}
	
	
	/**
	 * metodo que permite agregar un propietario a un vehiculo<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas, vehiculos<br>
	 * <b>post:</b> se ha agregado un propuetario a un vehiculo
	 * @param nAplellidos los apellidos del propuetario nApellidos!=""
	 * @param nDireccion la direccion del propietario nDireccion!=""
	 * @param nIdentificacion la identificacion del propietario nIdentificacion>0
	 * @param nNombres los nombres del propietario nNombres!=""
	 * @param nTelefono el telefono del propietario telefono>0
	 * @param nPlaca la placa del vehiculo al cual se va a agregar el propuetario
	 */
	public void agregarPropietario(String nAplellidos, String nDireccion, int nIdentificacion, String nNombres, int nTelefono, String nPlaca)
	{
            for(int i=0;i<marcas.size();i++){
                Marca miMarca = marcas.get(i);
                ArrayList<Linea> misLineas = miMarca.getLineas();
                for(int j=0;j<misLineas.size();j++){
                    Linea miLinea = misLineas.get(j);
                    ArrayList<Vehiculo> misVehiculos = miLinea.getVehiculos();
                    for(int k=0;k<misVehiculos.size();k++){
                        Vehiculo miVehiculo = misVehiculos.get(k);
                        if(miVehiculo.getPlaca().equals(nPlaca)){
                            Propietario miPropietario = miVehiculo.getPropietario();
                            if(miPropietario==null){
                                Propietario p=new Propietario(nIdentificacion, nNombres, nAplellidos, nDireccion, nTelefono);
                                miVehiculo.getPropietarioDAO().agregar(miVehiculo, p);
                                miVehiculo.setPropietario(p);
                            }
                        }
                    }
                }
            }           
	}
	
	
	//------------------------------------------------------------------------------------------
	//GESTIONAR CLIENTE
	//------------------------------------------------------------------------------------------
	
	
	/**
	 * metodo que permite buscar un cliente por identificacion<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas, vehiculos, tiquetes<br>
	 * <b>post:</b> se  ha buscado y retornado el cliente con la identificacion pasada como paramtro
	 * @param nIdentificacion la identificacion con la que se busca al cliente nIdentificacion!=null
	 * @return el cliente con la identificacion pasada como parametro de lo contrario null
	 */
	public Cliente buscarCliente(int nIdentificacion){
            Cliente retorno = null;
		for(int i=0;i< clientes.size();i++){
                    Cliente miCliente = clientes.get(i);
                    if(miCliente.getIdentificacion()==nIdentificacion){
                        retorno= miCliente;
                    }
                }
            return retorno;
	}
	
        
	/**
	 * metodo que permite eliminar una cliente del programa<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas, vehiculos, tiquetes<br>
	 * <b>post:</b> se ha eliminado el cliente con la identificacion pasada como parametro del programa y la base de datos
	 * @param nIdentificacion la identificacion por la caual se elimina al cliente
	 */
	public void eliminarCliente(int nIdentificacion){
           Cliente buscado =buscarCliente(nIdentificacion);
           if(buscado!=null){
               clientes.remove(buscado);
               clienteDAO.eliminar(buscado);
           }
	}
	
	
	/**
	 * metodo que permite modificar un cliente<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas, vehiculos, tiquetes<br>
	 * <b>post</b> se ha modificado el cliente por los datos pasados como parametros
	 * @param nNombres los nombres del cliente nNombres!=""
	 * @param nApellidos los apellidos del cliente nApellidos!=""
	 * @param nIdentificacion la identificacion del cliente!=""
	 */
	public void modificarCliente(String nNombres, String nApellidos, int nIdentificacion){
            Cliente buscado=buscarCliente(nIdentificacion);
            if(buscado!=null){
                try {
                    buscado.setNombres(nNombres);
                    buscado.setApellidos(nApellidos);
                    clienteDAO.actualizar(buscado);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(RutasSuroccidente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
	}
	
	
	/**
	 * metodo que permite agregar un cliente a un tiquete<br>
	 * <b>pre:</b> se ha inicializado la lista de maracas, lineas, vehiculos, tiquetes<br>
	 * <b>post:</b> se ha agreagado un cliente a un tiquete
	 * @param nNombres los nombres del cliente nNombres!=""
	 * @param nApellidos los apellidos del cliente nApellidos!=""
	 * @param nIdentificacion la identificacion del cliente!=""
	 */
	public void agregarCliente(String nNombres, String nApellidos, int nIdentificacion){
            Cliente buscado=buscarCliente(nIdentificacion);
            if(buscado==null){
                Cliente c= new Cliente(nIdentificacion, nNombres, nApellidos);
                clientes.add(c);
                clienteDAO.agregar(c);
            }
	}
        
        
        /**
         * Asigna una ruta y turno a un vehículo.
         * <b>post:</b> se asignado una ruta y un turno al vehiculo cuya placa se pasa como parametro
         * @param rutaAsignar la ruta la cual se va a asignar ritaAsignar !=null
         * @param placa el numero de la placa del vehiculo al cualse se le va a asignar la placa placa!=null
         */
        public void asignarRutaTurnoVehiculo(RutaTurno rutaAsignar, String placa){
            Vehiculo vehiculo = buscarVehiculo(placa);
            vehiculo.setRutaTurno(rutaAsignar);
            vehiculo.getRutaTurnoDAO().agregar(null, null, vehiculo, rutaAsignar);
        }
        
        
        /**
         * permite vender un tiquete a un cliente y lo asigna a un vehiculo
         * <b>post:</b> se ha asignado un tiquete a un cliente y a un vehiculo
         * @param tiqueteVender el tiquete a vender
         * @param placa la placa del vehiculo a la cual se va a cargar el tiquete
         * @param identificacion el numero de identificacion del clinte
         */
        public void venderTiqueteACliente(Tiquete tiqueteVender, String placa, int identificacion){
            Vehiculo v=buscarVehiculo(placa);
            Cliente c= buscarCliente(identificacion);
            Tiquete[] tiquetesVehiculo=v.getTiquetes();
            for(int i=0;i<tiquetesVehiculo.length;i++){
                if(tiquetesVehiculo[i]==null){
                    numeroTiquetes++;
                    tiqueteVender.setNumero(numeroTiquetes);
                    tiquetesVehiculo[i]=tiqueteVender;
                    c.getTiquetes().add(tiqueteVender);
                    v.getTiqueteDAO().agregar(null, null, v, tiqueteVender);
                    c.getTiqueteDAO().agregar(null, null, c, tiqueteVender);
                }
            }
        }
        
        
        /**
         * permite cancelar la venta de un tiqute a un cliente y la descarga de la lista de vehiculos
         * <b>post:</b> se ha cancelado la venta del tiquete con la placa del vehiculo y el numerp de identificacion
         * @param tiqueteCancelar el numero de tiquete a cancelar
         * @param placa la placa del vehiculo a cancelar
         * @param identificacion el numero de identificacion del cliente a cancelar
         */
        public void cancelarVentaDeTiqueteACliente(Tiquete tiqueteCancelar){
            Cliente c=null;
            for(int i=0;i<clientes.size();i++){
                Cliente cliente = clientes.get(i);
                ArrayList<Tiquete> misTiquetes = cliente.getTiquetes();
                for(int j=0;j<misTiquetes.size();j++){
                    if(misTiquetes.get(j).getNumero()==tiqueteCancelar.getNumero()){
                        c=cliente;
                    }
                }
            }
            Vehiculo v=null;
            for(int i=0;i< marcas.size();i++){
                ArrayList<Linea> misLineas = marcas.get(i).getLineas();
                for(int j=0;j<misLineas.size();j++){
                    ArrayList<Vehiculo> misVehiculos = misLineas.get(j).getVehiculos();
                    for(int k=0;k< misVehiculos.size();k++){
                        Tiquete[] misTiquetes = misVehiculos.get(k).getTiquetes();
                        for(int m=0;m<misTiquetes.length;m++){
                            if(misTiquetes[m]!=null){
                                if(misTiquetes[m].getNumero()==tiqueteCancelar.getNumero()){
                                    v=misVehiculos.get(k);
                                }
                            }
                        }
                    }
                }
            }
            
            Tiquete[] tiquetesVehiculo=v.getTiquetes();
            for(int i=0;i<tiquetesVehiculo.length;i++){
                if(tiquetesVehiculo[i]==null){
                    if(tiquetesVehiculo[i].getNumero()== tiqueteCancelar.getNumero()){
                        tiquetesVehiculo[i]=null;
                        c.getTiquetes().remove(tiqueteCancelar);
                        v.getTiqueteDAO().agregar(null, null, v, tiqueteCancelar);
                        c.getTiqueteDAO().agregar(null, null, c, tiqueteCancelar);
                    }
                }
            }
        }
        
        
        /**
         * permite reportar el numero de tiquetes vendidos por vehiculo de acuerdo a una ruta y turno determinadas
         * <b>post:</b> se ha reportado el numero de tiquetes por la ruta y la placa
         * @param rutaReportar la ruta por la cual se desea dar el reporte
         * @return el numero de tiquetes vendidos
         */
        public int reportarNumeroTiquetesVendidosDeVehiculoPorRutaTurno(String placa){
            int contador=0;
            Vehiculo v = buscarVehiculo(placa);
            Tiquete[] misTiquetes = v.getTiquetes();
            for(int i=0;i<misTiquetes.length;i++){
                if(misTiquetes[i]!=null){
                    contador++;
                }
            }
            return contador;
        }
        
        
        /**
         * permite reportar el valor de tiquetes vendidos prara una ruta y un turno
         * <b>post:</b> se ha calculado y reportado el total de tiqutes vendidos  por la ruta y turno dada
         * @param rutaReportar la ruta y le turno por la cualse desea reportar lo tiquetes
         * @return el numero de tiqutes vendidos por una ruta y un turno pasadas como parametro
         */
        public double reportarValorTotalVentaTiquetesParaRutaTurno(RutaTurno rutaReportar){
            int contador = 0;
            for(int i=0;i<marcas.size();i++){
                ArrayList<Linea> misLineas = marcas.get(i).getLineas();
                for(int j=0;j< misLineas.size();j++){
                    ArrayList<Vehiculo> misVehiculos = misLineas.get(j).getVehiculos();
                    for(int k=0;k<misVehiculos.size();k++){
                        RutaTurno ruta = misVehiculos.get(k).getRutaTurno();
                        if(ruta!=null){
                            if(ruta.getCiudadDestino().equalsIgnoreCase(rutaReportar.getCiudadDestino())&& ruta.getCiudadOrigen().equalsIgnoreCase(rutaReportar.getCiudadOrigen())&&ruta.getHoraSalida().equals(rutaReportar.getHoraSalida())){
                                Tiquete[] misTiquetes = misVehiculos.get(k).getTiquetes();
                                for(int m=0;m<misTiquetes.length;m++){
                                    if(misTiquetes[m]!=null){
                                        contador++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return contador;
        }
        public ArrayList<RutaTurno> seleccionarRutasDistintas(){
            ArrayList<RutaTurno> listaRepetida = new ArrayList<RutaTurno>();
            for(int i=0;i< marcas.size();i++){
                ArrayList<Linea> misLineas = marcas.get(i).getLineas();
                for(int j=0;j<misLineas.size();j++){
                    ArrayList<Vehiculo> misVehiculos = misLineas.get(j).getVehiculos();
                    Vehiculo miVehiculo = misVehiculos.get(j);
                    RutaTurno miRuta = miVehiculo.getRutaTurno();
                    if(miRuta!=null){
                        listaRepetida.add(miRuta);
                    }
                }
            }
            ArrayList<RutaTurno> listaRetornar= new ArrayList<RutaTurno>();
            for(int i=0;i<listaRepetida.size();i++){
                RutaTurno ruta = listaRepetida.get(i);
                RutaTurno buscada= buscarRutaTurno(listaRepetida, ruta);
                if(buscada==null){
                    listaRetornar.add(ruta);
                }
            }
            return listaRetornar;
        }
        public RutaTurno buscarRutaTurno(ArrayList<RutaTurno> listaRutas, RutaTurno buscar){
            for(int i=0; i<listaRutas.size(); i++){
                RutaTurno ruta = listaRutas.get(i);
                if(ruta.getCiudadDestino().equalsIgnoreCase(buscar.getCiudadDestino())&& ruta.getCiudadOrigen().equalsIgnoreCase(buscar.getCiudadOrigen())&&ruta.getHoraSalida().equals(buscar.getHoraSalida())){
                    return ruta;
                }
            }
            return null;
        }
}