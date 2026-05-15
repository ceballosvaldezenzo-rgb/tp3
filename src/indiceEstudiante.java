public class indiceEstudiante {
    //***ATRIBUTOS DE LA CLASE
    private Estudiante[] tabla;
    private int tamaño;
    private int cantidadInsertados;

    //***CONSTRUCTOR
    public indiceEstudiante(){
        this.tamaño=23;
        this.tabla=new Estudiante[tamaño];
        this.cantidadInsertados=0;
    }

    //***METODOS

    //***METODO HASH
    private int hash(String legajo){
        int suma=0;
        for(int i=0;i<legajo.length();i++){
            suma=suma+legajo.charAt(i);
        }
        return suma%tamaño;
    }

    //***METODO PARA INSERTAR
    public void insertar(String legajo,Estudiante es){
        double factorCarga=(double)cantidadInsertados/tamaño;
        if(factorCarga>=0.75){
            System.out.println("Error la tabla hash ya esta llena");
            return;
        }
        int o=hash(legajo);
        int r=0;
        int p=0;//****Pasos en la exploracion
        while(tabla[o]!=null){
            p+=1;
            r=exploracion(o,p);
            o=r;
        }
        tabla[o]=es;
        cantidadInsertados+=1;
    }

    //***METODO PARA LA EXPLORACION
    private int exploracion(int o,int p){
        int funcion=(o+(p*p))%tamaño;
        return funcion;
    }


    //***METODO PARA BUSCAR
    public Estudiante buscar(String legajo){
       int o=0;//+++pasos para la exploracion
        int r=hash(legajo);//++++calcula la posicion segun el dni;

        while(tabla[r]!=null){
            if(tabla[r].getDocumento().equalsIgnoreCase(legajo)){
                return tabla[r];
            }
            o+=1;
            r=exploracion(r,o);
        }
        return null;
    }

}
