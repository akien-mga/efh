package ru.znay.znay.he.gfx.helper;

import ru.znay.znay.he.cfg.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Денис Сергеевич
 * Date: 15.04.12
 * Time: 19:01
 * To change this template use File | Settings | File Templates.
 */
public class TextFileHelper {

    public static List<String> LoadMessages() {
        InputStreamReader in = null;
        BufferedReader reader = null;
        try {
            in = new InputStreamReader(TextFileHelper.class.getResourceAsStream(Constants.DB_DIR + Constants.MESSAGES_FILE), Charset.forName("UTF-8"));
            reader = new BufferedReader(in);
            List<String> messages = new ArrayList<String>();
            String buff;
            while ((buff = reader.readLine()) != null) {
                buff = buff.substring(1);
                int index = buff.indexOf("//");
                messages.add((index == -1) ? buff : buff.substring(0, index));
            }
            return messages;
        } catch (Exception e) {
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    //ignored
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //ignored
                }
            }
        }
    }

    public static List<String> LoadQuestDB() {
        List<String> quests = new LinkedList<String>();
        InputStreamReader in = null;
        BufferedReader reader = null;

        try {
            in = new InputStreamReader(TextFileHelper.class.getResourceAsStream(Constants.DB_DIR + Constants.QUESTS_FILE), Charset.forName("UTF-8"));
            reader = new BufferedReader(in);
            String buff;
            while ((buff = reader.readLine()) != null) {
                //buff = buff.substring(0);
                int index = buff.indexOf("//");
                quests.add((index == -1) ? buff : buff.substring(0, index));
            }
            return quests;
        } catch (Exception e) {
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    //ignored
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //ignored
                }
            }
        }
    }
}
