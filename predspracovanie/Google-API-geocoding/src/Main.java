import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import de.siegmar.fastcsv.writer.CsvAppender;
import de.siegmar.fastcsv.writer.CsvWriter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.maps.model.AddressType.ACCOUNTING;

/**
 * Created by Tomáš Baránek on 3.3.2018.
 * FIIT STUBA
 * tomas.baranek1994@gmail.com
 */
public class Main {

    public static void main(String[] args) throws InterruptedException, ApiException, IOException {


        File trainfreeFile = new File("Train_free.csv");
        File locationClusterFile = new File("input.csv");
        File locationFile = new File("location.csv");

        File uprava101 = new File("uprava101.csv");

        File file = new File("uprava151.csv");


        CsvReader csvReader = new CsvReader();

        Map<String, Location> name = new HashMap<>();

        Map<String, LatLong> latLon = new HashMap<>();

        List<Cluster> clusters = new ArrayList<>();

        Map<Integer, Integer> clustersNumber = new HashMap<>();


        File test = new File("uprava120km.csv");

        int pocet = 0;
        try (CsvParser csvParser = csvReader.parse(test, StandardCharsets.UTF_8)) {
            CsvRow row = csvParser.nextRow();
            while ((row = csvParser.nextRow()) != null) {
                if(row.getField(0).equals("-100"))
                    pocet++;
            }
        }

        try (CsvParser csvParser = csvReader.parse(file, StandardCharsets.UTF_8)) {
            CsvRow row = csvParser.nextRow();
            while ((row = csvParser.nextRow()) != null) {
                Integer count = clustersNumber.containsKey(Integer.valueOf(row.getField(0))) ? clustersNumber.get(Integer.valueOf(row.getField(0))) : 0;
                clustersNumber.put(Integer.valueOf(row.getField(0)), count + 1);
            }
        }

        float priemer = 244768 / clustersNumber.size();
        pocet = 0;

        List<Integer> bigCluster = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : clustersNumber.entrySet()) {
            if(entry.getValue() < priemer) {
                bigCluster.add(entry.getKey());
            }
        }



        try (CsvParser csvParser = csvReader.parse(locationClusterFile, StandardCharsets.UTF_8)) {
            CsvRow row = csvParser.nextRow();
            while ((row = csvParser.nextRow()) != null) {
                clusters.add(new Cluster(Float.valueOf(row.getField(0)), Float.valueOf(row.getField(1)), Integer.valueOf(row.getField(2))));
            }
        }

        Map<String, Integer> maps = new HashMap<>();

        for (Cluster cluster: clusters) {
            maps.put(String.valueOf(cluster.lat) + String.valueOf(cluster.lng), cluster.cluster);
        }


        file = new File("uprava120km.csv");
        CsvWriter csvWriter = new CsvWriter();

        csvWriter.setFieldSeparator(',');
        csvWriter.setLineDelimiter("\r\n".toCharArray());
        csvWriter.setAlwaysDelimitText(true);

        try (CsvAppender csvAppender = csvWriter.append(file, StandardCharsets.UTF_8)) {
            // header
            csvAppender.appendField("cluster");
            csvAppender.endLine();

            try (CsvParser csvParser = csvReader.parse(uprava101, StandardCharsets.UTF_8)) {
                CsvRow row = csvParser.nextRow();
                while ((row = csvParser.nextRow()) != null) {
                    try {
                        if (!row.getField(2).equals("") &&
                                maps.get(String.valueOf(Float.valueOf(row.getField(2))) + String.valueOf(Float.valueOf(row.getField(3)))) != null) {
                            /*if(bigCluster.contains(maps.get(String.valueOf(Float.valueOf(row.getField(2))) + String.valueOf(Float.valueOf(row.getField(3)))))) {
                                csvAppender.appendField("-123456");
                            }else {*/
                                csvAppender.appendField(String.valueOf(maps.get(String.valueOf(Float.valueOf(row.getField(2))) + String.valueOf(Float.valueOf(row.getField(3))))));
                            //}
                            csvAppender.endLine();
                        } else {
                            csvAppender.appendField("-100");
                            csvAppender.endLine();
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                        csvAppender.appendField("-100");
                        csvAppender.endLine();
                    }
                }
            }

        }

//        File file = new File("uprava101.csv");
//        CsvWriter csvWriter = new CsvWriter();
//
//        csvWriter.setFieldSeparator(',');
//        csvWriter.setLineDelimiter("\r\n".toCharArray());
//        csvWriter.setAlwaysDelimitText(true);
//
//        try (CsvAppender csvAppender = csvWriter.append(file, StandardCharsets.UTF_8)) {
//            // header
//            csvAppender.appendField("Id");
//            csvAppender.appendField("LocationRaw");
//            csvAppender.appendField("Lat");
//            csvAppender.appendField("Log");
//            csvAppender.endLine();
//
//            try (CsvParser csvParser = csvReader.parse(trainfreeFile, StandardCharsets.UTF_8)) {
//                CsvRow row = csvParser.nextRow();
//                while ((row = csvParser.nextRow()) != null) {
//                    if (latLon.get(row.getField(1)) != null) {
//                        csvAppender.appendField("");
//                        csvAppender.appendField(row.getField(1));
//                        csvAppender.appendField(String.valueOf(latLon.get(row.getField(1)).lat));
//                        csvAppender.appendField(String.valueOf(latLon.get(row.getField(1)).lng));
//                        csvAppender.endLine();
//                    } else {
//                        csvAppender.appendField("");
//                        csvAppender.appendField(row.getField(1));
//                        csvAppender.endLine();
//                    }
//                }
//            }
//        }

        //Map<String, Cluster> clusters = new HashMap<>();

        //int pocet = 0;
        try (CsvParser csvParser = csvReader.parse(trainfreeFile, StandardCharsets.UTF_8)) {
            CsvRow row = csvParser.nextRow();
            while ((row = csvParser.nextRow()) != null) {
                //for()
                //clusters.put(row.)
            }
        }

        /*Map<String, CsvRow> rows = new HashMap<>();
        File file3 = new File("location.csv");
        CsvReader csvReader3 = new CsvReader();
        try (CsvParser csvParser3 = csvReader3.parse(file3, StandardCharsets.UTF_8)) {
            CsvRow row = row = csvParser3.nextRow();
            row = csvParser3.nextRow();
            while ((row = csvParser3.nextRow()) != null) {
                if(!rows.containsKey(row.getField(2))) {
                    rows.put(row.getField(2), row);
                }
            }
        }

        File file = new File("uprava.csv");
        CsvWriter csvWriter = new CsvWriter();

        csvWriter.setFieldSeparator(',');
        csvWriter.setLineDelimiter("\r\n".toCharArray());
        csvWriter.setAlwaysDelimitText(true);

        try (CsvAppender csvAppender = csvWriter.append(file, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, CsvRow> entry : rows.entrySet()) {
                if (entry.getValue().getField(3) != null && !entry.getValue().getField(3).equals("")) {
                    Float.valueOf(entry.getValue().getField(3));
                    csvAppender.appendField(entry.getValue().getField(3));
                    csvAppender.appendField(entry.getValue().getField(4));
                    csvAppender.endLine();
                }
            }

            System.out.println("");
        }

        /*File file = new File("foo40.csv");
        CsvWriter csvWriter = new CsvWriter();

        //csvWriter.setFieldSeparator(',');
        //csvWriter.setLineDelimiter("\r\n".toCharArray());
        //csvWriter.setAlwaysDelimitText(true);

        try (CsvAppender csvAppender = csvWriter.append(file, StandardCharsets.UTF_8)) {
            // header
            csvAppender.appendField("UUID");
            csvAppender.appendField("Salary");
            csvAppender.appendField("LocalityRaw");
            csvAppender.appendField("Latitude");
            csvAppender.appendField("Longitude");
            csvAppender.appendField("Long name");
            csvAppender.appendField("Short name");
            csvAppender.appendField("accounting");
            csvAppender.appendField("airport");
            csvAppender.appendField("amusement_park");
            csvAppender.appendField("aquarium");
            csvAppender.appendField("art_gallery");
            csvAppender.appendField("atm");
            csvAppender.appendField("bakery");
            csvAppender.appendField("bank");
            csvAppender.appendField("bar");
            csvAppender.appendField("beauty_salon");
            csvAppender.appendField("bicycle_store");
            csvAppender.appendField("book_store");
            csvAppender.appendField("bowling_alley");
            csvAppender.appendField("bus_station");
            csvAppender.appendField("cafe");
            csvAppender.appendField("campground");
            csvAppender.appendField("car_dealer");
            csvAppender.appendField("car_rental");
            csvAppender.appendField("car_repair");
            csvAppender.appendField("car_wash");
            csvAppender.appendField("casino");
            csvAppender.appendField("cemetery");
            csvAppender.appendField("church");
            csvAppender.appendField("city_hall");
            csvAppender.appendField("clothing_store");
            csvAppender.appendField("convenience_store");
            csvAppender.appendField("courthouse");
            csvAppender.appendField("dentist");
            csvAppender.appendField("department_store");
            csvAppender.appendField("doctor");
            csvAppender.appendField("electrician");
            csvAppender.appendField("electronics_store");
            csvAppender.appendField("embassy");
            csvAppender.appendField("fire_station");
            csvAppender.appendField("florist");
            csvAppender.appendField("funeral_home");
            csvAppender.appendField("furniture_store");
            csvAppender.appendField("gas_station");
            csvAppender.appendField("gym");
            csvAppender.appendField("hair_care");
            csvAppender.appendField("hardware_store");
            csvAppender.appendField("hindu_temple");
            csvAppender.appendField("home_goods_store");
            csvAppender.appendField("hospital");
            csvAppender.appendField("insurance_agency");
            csvAppender.appendField("jewelry_store");
            csvAppender.appendField("laundry");
            csvAppender.appendField("lawyer");
            csvAppender.appendField("library");
            csvAppender.appendField("liquor_store");
            csvAppender.appendField("local_government_office");
            csvAppender.appendField("locksmith");
            csvAppender.appendField("lodging");
            csvAppender.appendField("meal_delivery");
            csvAppender.appendField("meal_takeaway");
            csvAppender.appendField("mosque");
            csvAppender.appendField("movie_rental");
            csvAppender.appendField("movie_theater");
            csvAppender.appendField("moving_company");
            csvAppender.appendField("museum");
            csvAppender.appendField("night_club");
            csvAppender.appendField("painter");
            csvAppender.appendField("park");
            csvAppender.appendField("parking");
            csvAppender.appendField("pet_store");
            csvAppender.appendField("pharmacy");
            csvAppender.appendField("physiotherapist");
            csvAppender.appendField("plumber");
            csvAppender.appendField("police");
            csvAppender.appendField("post_office");
            csvAppender.appendField("real_estate_agency");
            csvAppender.appendField("real_estate_agency");
            csvAppender.appendField("roofing_contractor");
            csvAppender.appendField("rv_park");
            csvAppender.appendField("school");
            csvAppender.appendField("shoe_store");
            csvAppender.appendField("shopping_mall");
            csvAppender.appendField("spa");
            csvAppender.appendField("stadium");
            csvAppender.appendField("storage");
            csvAppender.appendField("store");
            csvAppender.appendField("subway_station");
            csvAppender.appendField("supermarket");
            csvAppender.appendField("synagogue");
            csvAppender.appendField("taxi_stand");
            csvAppender.appendField("train_station");
            csvAppender.appendField("transit_station");
            csvAppender.appendField("travel_agency");
            csvAppender.appendField("veterinary_care");
            csvAppender.appendField("zoo");
            csvAppender.appendField("administrative_area_level_1");
            csvAppender.appendField("administrative_area_level_2");
            csvAppender.appendField("administrative_area_level_3");
            csvAppender.appendField("administrative_area_level_4");
            csvAppender.appendField("administrative_area_level_5");
            csvAppender.appendField("colloquial_area");
            csvAppender.appendField("country");
            csvAppender.appendField("establishment");
            csvAppender.appendField("finance");
            csvAppender.appendField("floor");
            csvAppender.appendField("food");
            csvAppender.appendField("general_contractor");
            csvAppender.appendField("geocode");
            csvAppender.appendField("health");
            csvAppender.appendField("intersection");
            csvAppender.appendField("locality");
            csvAppender.appendField("natural_feature");
            csvAppender.appendField("neighborhood");
            csvAppender.appendField("place_of_worship");
            csvAppender.appendField("political");
            csvAppender.appendField("point_of_interest");
            csvAppender.appendField("post_box");
            csvAppender.appendField("postal_code");
            csvAppender.appendField("postal_code_prefix");
            csvAppender.appendField("postal_code_suffix");
            csvAppender.appendField("postal_town");
            csvAppender.appendField("premise");
            csvAppender.appendField("room");
            csvAppender.appendField("route");
            csvAppender.appendField("street_address");
            csvAppender.appendField("street_number");
            csvAppender.appendField("sublocality");
            csvAppender.appendField("sublocality_level_4");
            csvAppender.appendField("sublocality_level_5");
            csvAppender.appendField("sublocality_level_3");
            csvAppender.appendField("sublocality_level_2");
            csvAppender.appendField("sublocality_level_1");
            csvAppender.appendField("subpremise");
            csvAppender.endLine();
            Map<String, CsvRow> rows = new HashMap<>();
            File file3 = new File("location.csv");
            CsvReader csvReader3 = new CsvReader();
            try (CsvParser csvParser3 = csvReader3.parse(file3, StandardCharsets.UTF_8)) {
                CsvRow row = row = csvParser3.nextRow();
                row = csvParser3.nextRow();
                while ((row = csvParser3.nextRow()) != null) {
                    if(!rows.containsKey(row.getField(2))) {
                        rows.put(row.getField(2), row);
                    }
                }
            }

            Map<String, GeocodingResult[]> resultMap = new HashMap<>();
            File file2 = new File("Train_rev1.csv");
            CsvReader csvReader = new CsvReader();

            try (CsvParser csvParser = csvReader.parse(file2, StandardCharsets.UTF_8)) {
                CsvRow row = row = csvParser.nextRow();
                while(!row.getField(0).equals("72683871")){
                    System.out.println();
                    row = csvParser.nextRow();
                }
                int i = 0;
                while ((row = csvParser.nextRow()) != null) {
                    GeoApiContext context = null;
                    switch (i) {
                        case 0:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyAyVJPP4Ml-JyAxCX0NlrVlUxXfVYv5jHU")
                                    .build();
                            i = 1;
                            break;
                        case 1:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyAvC5qPAvPvGP79bPBHAKBKxt2GqRkxYAE")
                                    .build();
                            i = 2;
                            break;
                        case 2:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyCzJ5-QuWrMTbu2bTHtGvKaPSNHDYrkmvc")
                                    .build();
                            i = 3;
                            break;
                        case 3:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyCHCG9GukmtGF2PiRTrm_LV-q_a0BNkuxQ")
                                    .build();
                            i = 4;
                            break;
                        case 4:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyDwG8yXBIip5Qx6yTwrqEqMshIkKuIrnqM")
                                    .build();
                            i = 5;
                            break;
                        case 5:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyBvyUfak-wkEtbMCR4OvGN_j2hTUf1ugFM")
                                    .build();
                            i = 6;
                            break;
                        case 6:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyAuPO3KCABR3A9OwrzOfajR8-5ZLB_Wz0w")
                                    .build();
                            i = 7;
                            break;
                        case 7:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyA8LFafgP0GIK9EKxP642_3m-VXibPDXjo")
                                    .build();
                            i = 8;
                            break;
                        case 8:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyAcEA54GHeEDSAcwrnynB3LmkXYrSGzNLk")
                                    .build();
                            i = 9;
                            break;
                        case 9:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyBbYnSFdx9tweFGaYYMrOmOgsLXMXsh2zA")
                                    .build();
                            i = 10;
                            break;
                        case 10:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyCPyx9j-_RK94DXPbZqEAXURbFCDGz2IN8")
                                    .build();
                            i = 11;
                            break;
                        case 11:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyBXmFMuBP_apjiEeWDj3O6lPFRrUswblnA")
                                    .build();
                            i = 12;
                            break;
                        case 12:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyCvZmfX0kBcEtTFrL3XSlfWNeP103KxW0U")
                                    .build();
                            i = 13;
                            break;
                        case 13:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyCDjA4s_neUxvHlnVVk_1VmrtYd9V7aLZg")
                                    .build();
                            i = 14;
                            break;
                        case 14:
                            context = new GeoApiContext.Builder()
                                    .apiKey("AIzaSyDvK60p1IKai0gk47mlOlkEuz8tWcGLKvg")
                                    .build();
                            i = 0;
                            break;
                    }
                    GeocodingResult[] results;
                    if(rows.get(row.getField(3)) != null) {
                        csvAppender.appendField(row.getField(0));
                        csvAppender.appendField(row.getField(10));
                        csvAppender.appendField(row.getField(3));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(3));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(4));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(5));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(6));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(7));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(8));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(9));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(10));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(11));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(12));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(13));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(14));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(15));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(16));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(17));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(18));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(19));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(20));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(21));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(22));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(23));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(24));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(25));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(26));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(27));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(28));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(29));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(30));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(31));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(32));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(33));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(34));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(35));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(36));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(37));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(38));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(39));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(40));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(41));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(42));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(43));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(44));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(45));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(46));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(47));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(48));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(49));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(50));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(51));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(52));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(53));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(54));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(55));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(56));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(57));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(58));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(59));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(60));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(61));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(62));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(63));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(64));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(65));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(66));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(67));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(68));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(69));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(70));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(71));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(72));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(73));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(74));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(75));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(76));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(77));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(78));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(79));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(80));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(81));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(82));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(83));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(84));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(85));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(86));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(87));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(88));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(89));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(90));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(91));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(92));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(93));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(94));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(95));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(96));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(97));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(98));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(99));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(100));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(101));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(102));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(103));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(104));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(105));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(106));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(107));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(108));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(109));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(110));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(111));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(112));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(113));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(114));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(115));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(116));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(117));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(118));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(119));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(120));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(121));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(122));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(123));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(124));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(125));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(126));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(127));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(128));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(129));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(130));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(131));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(132));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(133));
                        csvAppender.appendField(rows.get(row.getField(3)).getField(134));
                        csvAppender.endLine();
                    }else {
                        if (resultMap.get(row.getField(3)) != null) {
                            results = resultMap.get(row.getField(3));
                        } else {
                            results = GeocodingApi.geocode(context,
                                    row.getField(3)).await();
                            resultMap.put(row.getField(3), results);
                        }
                        if(results.length == 0) {
                            csvAppender.appendField(row.getField(0));
                            csvAppender.appendField(row.getField(10));
                            csvAppender.appendField(row.getField(3));
                            csvAppender.endLine();
                        } else {
                            for (GeocodingResult result : results) {
                                csvAppender.appendField(row.getField(0));
                                csvAppender.appendField(row.getField(10));
                                csvAppender.appendField(row.getField(3));
                                csvAppender.appendField(String.valueOf(result.geometry.location.lat));
                                csvAppender.appendField(String.valueOf(result.geometry.location.lng));
                                if (result.addressComponents[0] != null) {
                                    csvAppender.appendField(result.addressComponents[0].longName);
                                    csvAppender.appendField(result.addressComponents[0].shortName);
                                } else {
                                    csvAppender.appendField("");
                                    csvAppender.appendField("");
                                }
                                int[] pole = new int[128];
                                for (AddressType addressType : result.types) {

                                    if (addressType.toString().equals("accounting"))
                                        pole[0] = 1;

                                    if (addressType.toString().equals("airport"))
                                        pole[1] = 1;

                                    if (addressType.toString().equals("amusement_park"))
                                        pole[2] = 1;

                                    if (addressType.toString().equals("aquarium"))
                                        pole[3] = 1;

                                    if (addressType.toString().equals("art_gallery"))
                                        pole[4] = 1;

                                    if (addressType.toString().equals("atm"))
                                        pole[5] = 1;

                                    if (addressType.toString().equals("bakery"))
                                        pole[6] = 1;

                                    if (addressType.toString().equals("bank"))
                                        pole[7] = 1;

                                    if (addressType.toString().equals("bar"))
                                        pole[8] = 1;

                                    if (addressType.toString().equals("beauty_salon"))
                                        pole[9] = 1;

                                    if (addressType.toString().equals("bicycle_store"))
                                        pole[10] = 1;

                                    if (addressType.toString().equals("book_store"))
                                        pole[11] = 1;

                                    if (addressType.toString().equals("bowling_alley"))
                                        pole[12] = 1;

                                    if (addressType.toString().equals("bus_station"))
                                        pole[13] = 1;

                                    if (addressType.toString().equals("cafe"))
                                        pole[14] = 1;

                                    if (addressType.toString().equals("campground"))
                                        pole[15] = 1;

                                    if (addressType.toString().equals("car_dealer"))
                                        pole[16] = 1;

                                    if (addressType.toString().equals("car_rental"))
                                        pole[17] = 1;

                                    if (addressType.toString().equals("car_repair"))
                                        pole[18] = 1;

                                    if (addressType.toString().equals("car_wash"))
                                        pole[19] = 1;

                                    if (addressType.toString().equals("casino"))
                                        pole[20] = 1;

                                    if (addressType.toString().equals("cemetery"))
                                        pole[21] = 1;

                                    if (addressType.toString().equals("church"))
                                        pole[22] = 1;

                                    if (addressType.toString().equals("city_hall"))
                                        pole[23] = 1;

                                    if (addressType.toString().equals("clothing_store"))
                                        pole[24] = 1;

                                    if (addressType.toString().equals("convenience_store"))
                                        pole[25] = 1;

                                    if (addressType.toString().equals("courthouse"))
                                        pole[26] = 1;

                                    if (addressType.toString().equals("dentist"))
                                        pole[27] = 1;

                                    if (addressType.toString().equals("department_store"))
                                        pole[28] = 1;

                                    if (addressType.toString().equals("doctor"))
                                        pole[29] = 1;

                                    if (addressType.toString().equals("electrician"))
                                        pole[30] = 1;

                                    if (addressType.toString().equals("electronics_store"))
                                        pole[31] = 1;

                                    if (addressType.toString().equals("embassy"))
                                        pole[32] = 1;

                                    if (addressType.toString().equals("fire_station"))
                                        pole[33] = 1;

                                    if (addressType.toString().equals("florist"))
                                        pole[34] = 1;

                                    if (addressType.toString().equals("funeral_home"))
                                        pole[35] = 1;

                                    if (addressType.toString().equals("furniture_store"))
                                        pole[36] = 1;

                                    if (addressType.toString().equals("gas_station"))
                                        pole[37] = 1;

                                    if (addressType.toString().equals("gym"))
                                        pole[38] = 1;

                                    if (addressType.toString().equals("hair_care"))
                                        pole[39] = 1;

                                    if (addressType.toString().equals("hardware_store"))
                                        pole[40] = 1;

                                    if (addressType.toString().equals("hindu_temple"))
                                        pole[41] = 1;

                                    if (addressType.toString().equals("home_goods_store"))
                                        pole[42] = 1;

                                    if (addressType.toString().equals("hospital"))
                                        pole[43] = 1;

                                    if (addressType.toString().equals("insurance_agency"))
                                        pole[44] = 1;

                                    if (addressType.toString().equals("jewelry_store"))
                                        pole[45] = 1;

                                    if (addressType.toString().equals("laundry"))
                                        pole[46] = 1;

                                    if (addressType.toString().equals("lawyer"))
                                        pole[47] = 1;

                                    if (addressType.toString().equals("library"))
                                        pole[48] = 1;

                                    if (addressType.toString().equals("liquor_store"))
                                        pole[49] = 1;

                                    if (addressType.toString().equals("local_government_office"))
                                        pole[50] = 1;

                                    if (addressType.toString().equals("locksmith"))
                                        pole[51] = 1;

                                    if (addressType.toString().equals("lodging"))
                                        pole[52] = 1;

                                    if (addressType.toString().equals("meal_delivery"))
                                        pole[53] = 1;

                                    if (addressType.toString().equals("meal_takeaway"))
                                        pole[54] = 1;

                                    if (addressType.toString().equals("mosque"))
                                        pole[55] = 1;

                                    if (addressType.toString().equals("movie_rental"))
                                        pole[56] = 1;

                                    if (addressType.toString().equals("movie_theater"))
                                        pole[57] = 1;

                                    if (addressType.toString().equals("moving_company"))
                                        pole[58] = 1;

                                    if (addressType.toString().equals("museum"))
                                        pole[59] = 1;

                                    if (addressType.toString().equals("night_club"))
                                        pole[60] = 1;

                                    if (addressType.toString().equals("painter"))
                                        pole[61] = 1;

                                    if (addressType.toString().equals("park"))
                                        pole[62] = 1;

                                    if (addressType.toString().equals("parking"))
                                        pole[63] = 1;

                                    if (addressType.toString().equals("pet_store"))
                                        pole[64] = 1;

                                    if (addressType.toString().equals("pharmacy"))
                                        pole[65] = 1;

                                    if (addressType.toString().equals("physiotherapist"))
                                        pole[66] = 1;

                                    if (addressType.toString().equals("plumber"))
                                        pole[67] = 1;

                                    if (addressType.toString().equals("police"))
                                        pole[68] = 1;

                                    if (addressType.toString().equals("post_office"))
                                        pole[69] = 1;

                                    if (addressType.toString().equals("real_estate_agency"))
                                        pole[70] = 1;

                                    if (addressType.toString().equals("restaurant"))
                                        pole[71] = 1;

                                    if (addressType.toString().equals("roofing_contractor"))
                                        pole[72] = 1;

                                    if (addressType.toString().equals("rv_park"))
                                        pole[73] = 1;

                                    if (addressType.toString().equals("school"))
                                        pole[74] = 1;

                                    if (addressType.toString().equals("shoe_store"))
                                        pole[75] = 1;

                                    if (addressType.toString().equals("shopping_mall"))
                                        pole[76] = 1;

                                    if (addressType.toString().equals("spa"))
                                        pole[77] = 1;

                                    if (addressType.toString().equals("stadium"))
                                        pole[78] = 1;

                                    if (addressType.toString().equals("storage"))
                                        pole[79] = 1;

                                    if (addressType.toString().equals("store"))
                                        pole[80] = 1;

                                    if (addressType.toString().equals("subway_station"))
                                        pole[81] = 1;

                                    if (addressType.toString().equals("supermarket"))
                                        pole[82] = 1;

                                    if (addressType.toString().equals("synagogue"))
                                        pole[83] = 1;

                                    if (addressType.toString().equals("taxi_stand"))
                                        pole[84] = 1;

                                    if (addressType.toString().equals("train_station"))
                                        pole[85] = 1;

                                    if (addressType.toString().equals("transit_station"))
                                        pole[86] = 1;

                                    if (addressType.toString().equals("travel_agency"))
                                        pole[87] = 1;

                                    if (addressType.toString().equals("veterinary_care"))
                                        pole[88] = 1;

                                    if (addressType.toString().equals("zoo"))
                                        pole[89] = 1;

                                    if (addressType.toString().equals("administrative_area_level_1"))
                                        pole[90] = 1;

                                    if (addressType.toString().equals("administrative_area_level_2"))
                                        pole[91] = 1;

                                    if (addressType.toString().equals("administrative_area_level_3"))
                                        pole[92] = 1;

                                    if (addressType.toString().equals("administrative_area_level_4"))
                                        pole[93] = 1;

                                    if (addressType.toString().equals("administrative_area_level_5"))
                                        pole[94] = 1;

                                    if (addressType.toString().equals("colloquial_area"))
                                        pole[95] = 1;

                                    if (addressType.toString().equals("country"))
                                        pole[96] = 1;

                                    if (addressType.toString().equals("establishment"))
                                        pole[97] = 1;

                                    if (addressType.toString().equals("finance"))
                                        pole[98] = 1;

                                    if (addressType.toString().equals("floor"))
                                        pole[99] = 1;

                                    if (addressType.toString().equals("food"))
                                        pole[100] = 1;

                                    if (addressType.toString().equals("general_contractor"))
                                        pole[101] = 1;

                                    if (addressType.toString().equals("geocode"))
                                        pole[102] = 1;

                                    if (addressType.toString().equals("health"))
                                        pole[103] = 1;

                                    if (addressType.toString().equals("intersection"))
                                        pole[104] = 1;

                                    if (addressType.toString().equals("locality"))
                                        pole[105] = 1;

                                    if (addressType.toString().equals("natural_feature"))
                                        pole[106] = 1;

                                    if (addressType.toString().equals("neighborhood"))
                                        pole[107] = 1;

                                    if (addressType.toString().equals("place_of_worship"))
                                        pole[108] = 1;

                                    if (addressType.toString().equals("political"))
                                        pole[109] = 1;

                                    if (addressType.toString().equals("point_of_interest"))
                                        pole[110] = 1;

                                    if (addressType.toString().equals("post_box"))
                                        pole[111] = 1;

                                    if (addressType.toString().equals("postal_code"))
                                        pole[112] = 1;

                                    if (addressType.toString().equals("postal_code_prefix"))
                                        pole[113] = 1;

                                    if (addressType.toString().equals("postal_code_suffix"))
                                        pole[114] = 1;

                                    if (addressType.toString().equals("postal_town"))
                                        pole[115] = 1;

                                    if (addressType.toString().equals("premise"))
                                        pole[116] = 1;

                                    if (addressType.toString().equals("room"))
                                        pole[117] = 1;

                                    if (addressType.toString().equals("route"))
                                        pole[118] = 1;

                                    if (addressType.toString().equals("street_address"))
                                        pole[119] = 1;

                                    if (addressType.toString().equals("street_number"))
                                        pole[120] = 1;

                                    if (addressType.toString().equals("sublocality"))
                                        pole[121] = 1;

                                    if (addressType.toString().equals("sublocality_level_4"))
                                        pole[122] = 1;

                                    if (addressType.toString().equals("sublocality_level_5"))
                                        pole[123] = 1;

                                    if (addressType.toString().equals("sublocality_level_3"))
                                        pole[124] = 1;

                                    if (addressType.toString().equals("sublocality_level_2"))
                                        pole[125] = 1;

                                    if (addressType.toString().equals("sublocality_level_1"))
                                        pole[126] = 1;

                                    if (addressType.toString().equals("subpremise"))
                                        pole[127] = 1;

                                }
                                for (int bit : pole) {
                                    csvAppender.appendField(String.valueOf(bit));
                                }
                                csvAppender.endLine();
                            }
                        }
                    }
                }
            }
        }

        /*if (results != null && results.length > 0 && results[0] != null && results[0].geometry != null && results[0].geometry.location != null) {
            double lat = results[0].geometry.location.lat;
            double lng = results[0].geometry.location.lng;
            response = response + "(" + team_index++ + ", '" + team + "', " + lat + ", " + lng + "),\n";
        }*/
    }
}
