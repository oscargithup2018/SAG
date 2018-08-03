package all.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class FileLogic{

    private static Properties properties;

    public static void writeObjeto(File rutaDestino, CenterLogic centerLogic) throws IOException {
        ObjectOutputStream writeObject = new ObjectOutputStream(new FileOutputStream(rutaDestino));
        writeObject.writeObject(centerLogic);
        writeObject.close();
    }

    public static CenterLogic readObjeto(Path ruta) throws IOException, ClassNotFoundException {
        if (ruta == null) {
            return null;
        } else {
            ObjectInputStream leerObject = new ObjectInputStream(new FileInputStream(ruta.toString()));
            CenterLogic cl = (CenterLogic) leerObject.readObject();
            return cl;
        }
    }

    public static void setLastPath(Path ultimaRuta) throws IOException {
        if (ultimaRuta != null) {
            properties.setProperty("sag.ultimaRuta", ultimaRuta.toString());
            properties.store(new FileOutputStream("./properties/sag.properties"), "Ultima Ruta Abrida");
        }
    }

//    public static void guardarAll(String ruta) throws IOException {
//        //Array ingresos
//        File f1 = new File(ruta);
//        FileWriter esc = new FileWriter(f1);
//
//        for (int i = 0; i < getListaIngresos().size(); i++) {
//            esc.write("1;" + getListaIngresos().get(i).getDescripcion() + ";" + getListaIngresos().get(i).getValor());
//            esc.write(System.getProperty("line.separator"));
//        }
//
//        //aray gastos
//        for (int i = 0; i < getListaGastos().size(); i++) {
//            esc.write("2;" + getListaGastos().get(i).getNombreG() + ";" + getListaGastos().get(i).getValorGasto() + ";" + getListaGastos().get(i).getDescripcion());
//            esc.write(System.getProperty("line.separator"));
//        }
//        esc.close();
//    }


    public static Path lastPathOpen() throws IOException {
        Path ultimaRuta;
        properties = new Properties();

        //Verificación de existencia del archivo de propiedades
        File fileProperties = new File("./properties");
        File aProperties = new File("./properties/sag.properties");
        //Crear directorio para las propiedades
        if (!Files.exists(fileProperties.toPath())) {
            Files.createDirectory(fileProperties.toPath());
        }
        //Crear archivo de propiedades si no existe
        if (!Files.exists(aProperties.toPath())) {
            Files.createFile(aProperties.toPath());
            FileWriter escribir = new FileWriter(aProperties);
            escribir.write("#Archivo de propiedades SAG" + System.lineSeparator() + "#Ultima Ruta" + System.lineSeparator() + "sag.ultimaRuta = ");
            escribir.close();
            return null; //indica que no se ha abierto ningun archivo, por lo que debe mostrarse vacía la app
        } else {
            //Si existe el archivo de propiedades retornar la ruta especificada
            properties.load(new InputStreamReader(new FileInputStream(aProperties)));
            ultimaRuta = Paths.get(properties.getProperty("sag.ultimaRuta"));

            if (ultimaRuta.toString().isEmpty()) { //Cuando la ruta no existe en la clave
                System.out.println("No existe ruta en la clave: sag.ultimaRuta");
                return null;
            } else { //Cuando hay una posible ruta en la llave
                System.out.println("ultima ruta encontrada: " + ultimaRuta);

                if (!Files.exists(ultimaRuta)) {
                    return null;
                } else {
                    if (ultimaRuta.toString().endsWith(".sag")) {
                        return ultimaRuta;
                    } else {
                        return null;
                    }
                }
            }
        }
    }
}
