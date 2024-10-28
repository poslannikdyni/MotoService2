package ru.clevertec.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.clevertec.domain.Moto;
import ru.clevertec.service.MotoService;
import ru.clevertec.util.LocalDateSerializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet(name = "moto-servlet", value = "/moto")
public class MotoServlet extends HttpServlet {

    private final MotoService motoService = MotoService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Moto> motos = motoService.getAll();
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
            Gson gson = gsonBuilder.create();
            String json = gson.toJson(motos);
            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
                resp.setStatus(SC_OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
