package servlets;

import common.Constantes;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.Random;

@WebServlet(name = Constantes.SERVLET, value = Constantes.SERVLET2)
public class Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(Constantes.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);
        String template;
        String numElegido = req.getParameter(Constantes.NUM);
        Integer adivinarNum = (Integer) req.getSession().getAttribute(Constantes.RANDOM_NUMBER);
        Integer contador = (Integer) req.getSession().getAttribute(Constantes.CONTADOR);
        String answer= Constantes.EMPTY;
        if (adivinarNum == null) {
            adivinarNum = new Random().nextInt(10) + 1;
        }
        if (contador == null) {
            contador = 7;
        }
        if( numElegido.isEmpty() || numElegido.isBlank()){
            answer = Constantes.PON_UN_NUMERO_NO_SEAS_TIMIDO;}
        else if (Integer.parseInt(numElegido) == adivinarNum) {
            contador = 7;
            adivinarNum =  new Random().nextInt(10) + 1;
            answer = Constantes.ENHORABUENA_COMPADRE_HAS_ACERTADO;
        } else if (Integer.parseInt(numElegido) > adivinarNum) {
            contador--;
            answer = Constantes.ESE_NUMERIN_ES_MAS_GRANDE_QUE_LO_QUE_TE_QUEDA_DE_HIPOTECA;
        } else if (Integer.parseInt(numElegido) < adivinarNum) {
            contador--;
            answer = Constantes.ESE_NUMERO_ES_DEMASIADO_PEQUENYO_SE_PARECE_A_MI_CUENTA_BANCARIA;
        }

        if(contador == 0){
            contador = 7;
            adivinarNum = new Random().nextInt(10) + 1;
            answer = Constantes.NO_HAS_ACERTADO_ESPBILA_PALETO_Y_VUELVE_A_INTENTARLO;

        }
        context.setVariable(Constantes.ANSWER, answer);
        context.setVariable(Constantes.CONTADOR, contador);
        req.getSession().setAttribute(Constantes.CONTADOR, contador);
        req.getSession().setAttribute(Constantes.RANDOM_NUMBER, adivinarNum);
        template= Constantes.PARAM;
        templateEngine.process(template, context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
