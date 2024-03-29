package pizzashop.repository;

import pizzashop.model.MenuDataModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MenuRepository {
    private static final String FILENAME = "data/menu.txt";
    private List<MenuDataModel> listMenu;


    private void readMenu(){
        File file = new File(FILENAME);
        this.listMenu= new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line;
            while((line=br.readLine())!=null){
                MenuDataModel menuItem=getMenuItem(line);
                listMenu.add(menuItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MenuDataModel getMenuItem(String line){
        MenuDataModel item;
        if (line==null|| line.equals("")) return null;
        StringTokenizer st=new StringTokenizer(line, ",");
        String name= st.nextToken();
        double price = Double.parseDouble(st.nextToken());
        item = new MenuDataModel(name, 0, price);
        return item;
    }

    public List<MenuDataModel> getMenu(){
        readMenu();//create a new menu for each table, on request
        return listMenu;
    }
}