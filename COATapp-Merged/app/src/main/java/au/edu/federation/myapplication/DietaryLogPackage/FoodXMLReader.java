package au.edu.federation.myapplication.DietaryLogPackage;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madeleine on 16/09/2017.
 */

public class FoodXMLReader {

    private static final String ns = null;
    private XmlPullParserFactory xmlPullParserFactory;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
//            XmlPullParser parser = Xml.newPullParser();
//            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//            ;

                xmlPullParserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = xmlPullParserFactory.newPullParser();
                parser.setInput(in, null);
                parser.nextTag();


            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        int eventType = parser.getEventType();
        Log.i("TAG", "The event type is: " + eventType);

        parser.require(XmlPullParser.START_TAG, ns, "resources");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();

                switch (name) {
                    case "vegetable":
                        entries.add(readFood(parser, "vegetable"));
                        break;
                    case "fruit":
                        entries.add(readFood(parser, "fruit"));
                        break;
                    case "grain":
                        entries.add(readFood(parser, "grain"));
                        break;
                    case "meat":
                        entries.add(readFood(parser, "meat"));
                        break;
                    case "dairy":
                        entries.add(readFood(parser, "dairy"));
                        break;
                    case "other":
                        entries.add(readFood(parser, "other"));
                        break;
                    case "sometimes":
                        entries.add(readFood(parser, "sometimes"));
                        break;
                }
            }
            //parser.nextTag();
        }
        return entries;
    }

    public static class Entry {
        public final String name;
        public final String serving;
        public final String kj;
        public final String category;
        public String drawableName;
        public String drawableID;

        //TODO: Somewhere in here.... catergories must be fixed so that we can sort the gridview into more gridviews
        //TODO: Extract Entry into it's own class...
        //But for now... idgaf.

        private Entry(String name, String serving, String kj, String category, String drawableName, String drawableID) {
            this.name = name;
            this.serving = serving;
            this.kj = kj;
            this.category = category;
            this.drawableID = drawableID;
            this.drawableName = drawableName;
        }
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private Entry readFood(XmlPullParser parser, String category) throws XmlPullParserException, IOException {
        //parser.require(XmlPullParser.START_TAG, ns, "entry");
        String name = null;
        String serving = null;
        String kj = null;
        String drawableId = null;

        while (parser.next() != XmlPullParser.END_TAG) {

            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            //If statements didnt cycle to next tag, block below works but less error checky.
//
//            String TAG = "XML read";
//
//            if (parser.getName().equals("name")) {
//                Log.d(TAG, "readFood: reading Name...");
//                name = readName(parser);
//                Log.d(TAG, "readFood: " + name);
//            } else if (name.equals("serving")) {
//                Log.d(TAG, "readFood: reading serving...");
//                serving = readServing(parser);
//                Log.d(TAG, "readFood: " + serving);
//            } else if (name.equals("kj")) {
//                Log.d(TAG, "readFood: reading kj...");
//                kj = readKj(parser);
//                Log.d(TAG, "readFood: " + kj);
//            } else {
//                skip(parser);
//            }

            Log.d("halp", "readFoodXML: " + parser.getName());

            String TAG = "XML read";

            if (parser.getName().equals("name")) {
                Log.d(TAG, "readFood: reading Name...");
                name = readName(parser);
                Log.d(TAG, "readFood: " + name);
                parser.nextTag();
                Log.d(TAG, "readFood: reading serving...");
                serving = readServing(parser);
                Log.d(TAG, "readFood: " + serving);
                parser.nextTag();
                Log.d(TAG, "readFood: reading kj...");
                kj = readKj(parser);
                Log.d(TAG, "readFood: " + kj);
                parser.nextTag();
                Log.d(TAG, "readFood: reading drawableName...");
                drawableId = readImage(parser);
            }
        }
        Log.d("ITEM", name);
        return new Entry(name, serving, kj, category, name, drawableId);
    }

    // Processes Name tags in the feed.
    private String readName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "name");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "name");
        return title;
    }

    // Processes Serving tags in the feed.
    private String readServing(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "serving");
        String serving = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "serving");
        return serving;
    }

    // Processes kj tags in the feed.
    private String readKj(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "kj");
        String kj = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "kj");
        return kj;
    }

    // Processes imageresource tags in the feed.
    private String readImage(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "image");
        String image = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "image");
        return image;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


}
