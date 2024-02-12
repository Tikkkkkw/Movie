import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles(){
        System.out.print("Enter a tital search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private ArrayList<String> casts () {

        ArrayList<String> allCastList = new ArrayList<String>();

        for (Movie movie : movies) {
            String[] cast = movie.getCast().split("\\|");
            for (String ppl : cast) {
                if (!(allCastList.contains(ppl))) {
                    allCastList.add(ppl);
                }
            }

        }
        Collections.sort(allCastList);
        return allCastList;
    }

    private void searchCast() {
        ArrayList<String> cast = casts();
        System.out.print("Enter cast keywords term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();
        ArrayList<String> results = new ArrayList<String>();

        for (int i = 0; i < cast.size(); i++) {

            if (cast.get(i).contains(searchTerm)) {
                results.add(cast.get(i));
            }
        }
        for (int i = 0; i < results.size(); i++)
        {
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + results.get(i));
        }
        System.out.println("Which cast do you want to know more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedCast = results.get(choice - 1);
        ArrayList<Movie> movieWithCast = new ArrayList<Movie>();

        for (Movie movie : movies) {
            String movieCast = movie.getCast();

            if (movieCast.contains(selectedCast)) {
                movieWithCast.add(movie);
            }
        }
        sortResults(movieWithCast);

        // now, display them all to the user
        for (int i = 0; i < movieWithCast.size(); i++)
        {
            String title = movieWithCast.get(i).getTitle();

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int c = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = movieWithCast.get(c - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords(){
        System.out.print("Enter keywords search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (Movie movie : movies) {
            String movieKeyword = movie.getKeywords();
            movieKeyword = movieKeyword.toLowerCase();

            if (movieKeyword.contains(searchTerm)) {
                //add the Movie objest to the results list
                results.add(movie);
            }
        }

        // sort the results by keyword
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private Map<String, ArrayList<Movie>> genres() {
        ArrayList<String> genre = new ArrayList<String>();
        int i = 0;
        for (Movie movie : movies) {
            i++;
//            String g =  movie.getGenres();
            String[] g = (movie.getGenres().split("\\|"));
            for (String genreName : g) {
                if (!genre.contains(genreName)) {
                    genre.add(genreName);

                }
            }
        }
        HashMap<String, ArrayList<Movie>> genreList = new HashMap<String, ArrayList<Movie>>();
        for (String s : genre) {
            ArrayList<Movie> moviesByGenre = new ArrayList<Movie>();
            genreList.put(s, moviesByGenre);
        }
        for (Movie movie : movies){
            String[] g = (movie.getGenres().split("\\|"));
            for (String s : g) {
                ArrayList<Movie> movieList = genreList.get(s);
                movieList.add(movie);
            }
        }
        return genreList;
    }
    private void listGenres() {
        Map<String, ArrayList<Movie>> movieMap = genres();
        ArrayList<String> keyThing = new ArrayList<String>();

        ArrayList<Movie> results = new ArrayList<Movie>();

        for (Map.Entry<String, ArrayList<Movie>> key : movieMap.entrySet()){
            keyThing.add(key.getKey());
    }
            Collections.sort(keyThing);


        // now, display them all to the user
        for (int i = 0; i < keyThing.size(); i++)
        {
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + keyThing.get(i));
        }

        System.out.println("Which genere do you want to see more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        String kei = keyThing.get(choice-1);
        System.out.println(kei);

        results = (movieMap.get(kei));
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int c = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(c - 1);

        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }


    private void listHighestRated() {
        ArrayList<Movie> top50 = new ArrayList<Movie>();
        ArrayList<Double> d = new ArrayList<Double>();
        for (Movie m : movies) {
        d.add(m.getUserRating());
        d.sort(Collections.reverseOrder());

        }

        }

        private void listHighestRevenue ()
        {

        }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}
