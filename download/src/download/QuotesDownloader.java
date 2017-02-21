package download;
import java.io.FileOutputStream;

import java.io.IOException;

import java.net.MalformedURLException;

import java.net.URL;

import java.nio.channels.Channels;

import java.nio.channels.ReadableByteChannel;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.Calendar;



/**

 * Program to download historical quotes of a stock or index from 

 * Yahoo! Finance.

 *

 * The program takes the following arguments:

 * <ol>

 * <li> The ID of the stock or index

 * <li> The first date

 * <li> The second date

 * <li> The trading interval

 * <li> The directory where to save quotes

 * </ol>

 *

 * For more details, see

 * https://code.google.com/p/yahoo-finance-managed/wiki/csvHistQuotesDownload

 *

 * @author Andrey Glotov

 */

public class QuotesDownloader {



    /**

     * Daily trading interval.

     */

    public static final String INTERVAL_DAILY   = "d";



    /**

     * Weekly trading interval.

     */

    public static final String INTERVAL_WEEKLY  = "w";



    /**

     * Monthly trading interval.

     */

    public static final String INTERVAL_MONTHLY = "m";



    /**

     * Build URL for historical quotes download.

     *

     * @param id the ID of the stock or index

     * @param fromDate the first date

     * @param toDate the last date 

     * @param interval the interval of the trading periods

     * @return the URL for historical quotes download

     * @throws MalformedURLException if URL is invalid

     */

    private static final URL buildURL(String id, Calendar fromDate,

            Calendar toDate, String interval) throws MalformedURLException {

        // Start

        String url = "http://ichart.yahoo.com/table.csv?s=";



        // ID

        url += id;



        // From Date

        url += "&a=" + fromDate.get(Calendar.MONTH);

        url += "&b=" + fromDate.get(Calendar.DAY_OF_MONTH);

        url += "&c=" + fromDate.get(Calendar.YEAR);



        // To Date

        url += "&d=" + toDate.get(Calendar.MONTH);

        url += "&e=" + toDate.get(Calendar.DAY_OF_MONTH);

        url += "&f=" + toDate.get(Calendar.YEAR);



        // Interval

        url += "&g=" + interval;



        // Static part

        url += "&ignore=.csv";



        return new URL(url);

    }



    /**

     * Download historical quotes of a stock or index.

     *

     * @param id the ID of the stock or index

     * @param fromDate the first date

     * @param toDate the last date

     * @param interval the interval of the trading periods

     * @param dirName the directory to save the quotes

     * @throws IOException if an I/O error occurs

     */

    public static void download(String id, Calendar fromDate,

            Calendar toDate, String interval, String dirName) 

                    throws IOException {

        URL url = buildURL(id, fromDate, toDate, interval);

        System.out.println(url);

        

        ReadableByteChannel rbc = Channels.newChannel(url.openStream());



        String outFile = dirName + "/" + id + ".csv";



        FileOutputStream fos = new FileOutputStream(outFile);

        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        fos.close();

        rbc.close();

    }



    /**

     * The entry point to class & application.

     *

     * @param args the command-line arguments

     * @throws IOException if an I/O error occurs

     * @throws ParseException if an invalid date is specified

     */

    public static void main(String[] args) throws IOException, ParseException {

        if (args.length != 5) {

            System.err.println("Usage: QuotesDownloader <id> <from_date> " +

                    "<to_date> <interval> <dir>");

            System.exit(1);

        }



        String id = args[0];



        SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");



        Calendar fromDate = Calendar.getInstance();

        fromDate.setTime(sdf.parse(args[1]));



        Calendar toDate = Calendar.getInstance();

        toDate.setTime(sdf.parse(args[2]));



        String interval = args[3];



        String dirPath = args[4];



        download(id, fromDate, toDate, interval, dirPath);

    }



}

