package dao;

import java.io.*;

/**
 * Created by KundaLin on 17/6/6.
 */
public class BudgetDAO {
    public static int getBudget() {
        int budget = 0;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader("./config");
            bufferedReader = new BufferedReader(fileReader);
            String s = bufferedReader.readLine();
            budget = Integer.parseInt(s.substring(7));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileReader!=null) try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(bufferedReader!=null)try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return budget;
    }

    public static void setBudget(int budget) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter("./config");
            bufferedWriter = new BufferedWriter(fileWriter);
            String s = "Budget=" + budget;
            bufferedWriter.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedWriter!=null)try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fileWriter!=null)try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
