package ar.edu.utn.dds.k3003.model;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.dto.HechoDTO;
import io.github.cdimascio.dotenv.Dotenv;
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
    private final Dotenv dotenv;

    @Autowired
    public AgregadorBot(Fachada fachada) {
        super();
        this.fachada = fachada;
        this.dotenv = Dotenv.load(); 
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
        return dotenv.get("NOMBRE_BOT");
    }

    @Override
    public String getBotToken() {
        return dotenv.get("TOKEN_BOT");
    }

    @PostConstruct
    public void registerBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            System.out.println("✅ Bot de Telegram registrado exitosamente.");
        } catch (TelegramApiException e) {
            System.err.println("❌ Error al registrar el bot de Telegram:");
            e.printStackTrace();
        }
    }
}
