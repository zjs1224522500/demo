package me.elvis.connector;

import java.io.IOException;

import me.elvis.connector.http.HttpRequest;
import me.elvis.connector.http.HttpResponse;

public class StaticResourceProcessor {

  public void process(HttpRequest request, HttpResponse response) {
    try {
      response.sendStaticResource();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

}
