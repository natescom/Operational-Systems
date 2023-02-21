import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/****************************************************************
 * Autor: Nathan Ferraz da Silva
 * Matricula: 201911925
 * Inicio: 23/03/2021
 * Ultima alteracao: 27/03/2021
 * Nome: Jukebox
 * Funcao: Tocar musica
 ***************************************************************/
public abstract class Jukebox {
  private static final String DIRETORYPATH = "sound/"; // Diretorio dos audios
  private static Clip clip; // O audio

  /**
   * Tocar uma musica
   * 
   * @param fileName nome do arquivo de musica
   */
  public static void play(String fileName) {
    try {
      File audio = new File(Jukebox.class.getClassLoader().getResource(DIRETORYPATH + fileName).getFile()); // Cria um objeto File com o arquivo encontrado no endereco passado
      if (audio.exists()) { // Verifico se a instancia foi bem sucedida
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(audio); // Crio um AudioInputStream com o File criado
        clip = AudioSystem.getClip(); // Pego o clip do AudioSystem
        clip.open(inputStream); // Abro o clip com AudioInputStream criado
        clip.start(); // Inicio a musica
      } else {
        System.out.println("ERRO AUDIO NAO ENCONTRADO");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Pausa/Despausar a musica que esta tocando no momento
   */
  public static void pause() {
    if (clip != null) // Se meu clip foi instanciado
      if (clip.isRunning()) { // Verifico se o clip estah sendo executado
        clip.stop(); // Caso esteja paro
      } else { // Caso nao esteja
        clip.start(); // continua
      }
  }

  public static void stop() {
    if (clip != null) {
      clip.stop();
      clip = null;
    }
  }
}