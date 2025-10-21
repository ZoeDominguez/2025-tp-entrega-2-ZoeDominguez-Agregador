package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.app.Fachada; 
import ar.edu.utn.dds.k3003.dto.HechoDTO; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import jakarta.annotation.PostConstruct; 
import java.util.List;
import java.util.NoSuchElementException;

@Component 
public class AgregadorBot extends TelegramLongPollingBot {

    private final Fachada fachada; 

    @Autowired
    public AgregadorBot(Fachada fachada) {
        super();
        this.fachada = fachada;
    }

    @Override
    public void onUpdateReceived(Update update) {
        
        final String messageTextReceived = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());

        String responseText;

        try {
            if (messageTextReceived.startsWith("/hechos ")) {
                
                String nombreColeccion = messageTextReceived.substring(8).trim();
                
                if (nombreColeccion.isEmpty()) {
                    responseText = "Por favor, especifica un nombre de colección. Ejemplo: /hechos miColeccion";
                } else {
                    List<HechoDTO> hechos = fachada.hechos(nombreColeccion);
                    
                    // Formatea la respuesta
                    if (hechos.isEmpty()) {
                        responseText = "No se encontraron hechos para la colección: '" + nombreColeccion + "'.";
                    } else {
                        StringBuilder sb = new StringBuilder("Hechos para '" + nombreColeccion + "':\n\n");
                        for (HechoDTO hecho : hechos) {
                            sb.append("• Título: ").append(hecho.titulo()).append("\n");
                            sb.append("  (ID: ").append(hecho.id()).append(")\n\n");
                        }
                        responseText = sb.toString();
                    }
                }
            } else {
                responseText = "Comando no reconocido.\nUsa /hechos <nombreDeLaColeccion> para listar hechos.";
            }

        } catch (NoSuchElementException e) {
            responseText = "Error: La colección o sus fuentes no se encontraron.";
            e.printStackTrace();
        } catch (Exception e) {
            responseText = "Ocurrió un error al procesar tu solicitud.";
            e.printStackTrace();
        }
        
        message.setText(responseText);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        // El documento recomienda usar variables de entorno [cite: 508-509]
        // return System.getenv("NOMBRE_BOT");
        return "PruebaAgregador_bot";
    }

    @Override
    public String getBotToken() {
        // ¡¡ADVERTENCIA DE SEGURIDAD!!
        // No dejes tu token en el código. 
        // El documento recomienda usar variables de entorno [cite: 503, 512-513]
        // return System.getenv("TOKEN_BOT");
        return "8070715356:AAFe0zyKNozvQN5OX18E63-j7Sv7XxNfMQA";
    }

    @PostConstruct
    public void registerBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            System.out.println("Bot de Telegram registrado exitosamente.");
        } catch (TelegramApiException e) {
            System.err.println("Error al registrar el bot de Telegram:");
            e.printStackTrace();
        }
    }


}