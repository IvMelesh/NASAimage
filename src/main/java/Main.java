import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        String url = "https://api.nasa.gov/planetary/apod?api_key=0PTwueKM6PZQpsNSzcbGB4NhyMUsPJhbvEFPB89f";
        ObjectMapper mapper = new ObjectMapper();
        CloseableHttpResponse response = client.execute(new HttpGet(url));
        NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);

        String urlImage = answer.url;
        String[] array = urlImage.split("/");
        String filename = array[array.length-1];

        CloseableHttpResponse imageResponse = client.execute(new HttpGet(answer.url));
        FileOutputStream fos = new FileOutputStream(filename);
        imageResponse.getEntity().writeTo(fos);
    }
}
