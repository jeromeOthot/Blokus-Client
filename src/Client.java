import java.awt.*;
import javax.swing.*;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

// ceci est est un exemple d'interface utilisateur graphique qui pourrait être
// utilisée dans notre projet de jeu de société
public class Client
{
   public static void creerEtAffichierIug(Socket socketJoueur)
   {
      // pure quetion d'esthétique (mettez cette ligne en commentaire et vous
      //verrez la différence.
      JFrame.setDefaultLookAndFeelDecorated( true );

      // création de la fenêtre principale de l'application
      JFrame frame = new JFrame( "Trival Pursuit version de Jérôme Othot et Christian Létourneau" );

      // fait en sorte que l'application se termine si on ferme la fenêtre
      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

      // déclaration et création du panneau d'affichage
      BoardGame afficheur = new BoardGame(socketJoueur);

      // ajout du composant graphique dans le JFrame (ou plutôt dans son
      // "content pane")
      frame.getContentPane().add( afficheur );

      // création et lancement du thread associé au panneau (animation)
      Thread t = new Thread( afficheur );
      t.start();

      // dispose les composants dans la fenêtre, règle la taille de cell-ci et
      // l'affiche.
      frame.pack();
      frame.setSize( 1024, 768 );
      frame.setResizable( false );
      frame.setVisible( true );
   }

   //Mon super de beau MAIN adorer !!!
   public static void main( String[] args )
   {
      //Serveur oServeur = new Serveur();

      //Déclaration des variables
      int NoPort = 9000;
      String Adresse = "127.0.0.1"; //Localhost

      try
      {
         Adresse = JOptionPane.showInputDialog( null, "Entrez l'adresse du serveur :" );
         NoPort = Integer.parseInt( JOptionPane.showInputDialog( null, "Entrez le no du port du serveur :" ));
         InetAddress AdresseServeur = InetAddress.getByName(Adresse);
         System.out.println(AdresseServeur);

         //ServerSocket  Serveur = new ServerSocket( NoPort, 0, AdresseServeur );
         //SocketAddress AdresseSocket = Serveur.getLocalSocketAddress();

         // System.out.println(AdresseSocket);
         //Vérifie si le port est valide
         if(NoPort > 0 && NoPort < 65535)
         {
            if(! Adresse.trim().equals("") || Adresse != null)
            {
               Socket  Serveur = new Socket(Adresse, NoPort);
               AfficherFenetre(Serveur);

            }
            else
            {
               JOptionPane.showMessageDialog( null,
                                                 "Votre adresse est invalide !",
                                                 "Adresse invalide",
                                                 JOptionPane.ERROR_MESSAGE );
            }
            //connexionServeur = new SocketServeur();
            //Serveur.bind(AdresseSocket);
         }
         else
         {
            JOptionPane.showMessageDialog( null,
                                  "Le Numero du port est invalide, doit être comprit entre 0 et 65535",
                                  "Numero de port invalide",
                                  JOptionPane.ERROR_MESSAGE );
         }
      }
      catch ( SecurityException se)
      {
         System.err.println(se);
      }
      catch ( IOException se)
      {
         JOptionPane.showMessageDialog( null,
                                  "Imposible de se connecter au serveur, serveur non-disponible ou adresse incorrect !",
                                  "Erreur de connexion",
                                  JOptionPane.ERROR_MESSAGE );
      }
      catch( NumberFormatException e )
      {
         JOptionPane.showMessageDialog( null,
                                  "Le Numero du port est invalide, doit être comprit entre 0 et 65535",
                                  "Numero de port invalide",
                                  JOptionPane.ERROR_MESSAGE );
      }
   }

    private static void AfficherFenetre(Socket socketJoueur )
    {
      //final int nbJoueurs_ = nbJoueurs;
      final Socket socketJoueur_ = socketJoueur;
      // demande au thread responsable des événements de voir lui-même à la
      // création et à l'affichage de l'IUG pour ne pas courir le risque que
      // l'application gèle au démarrage
      javax.swing.SwingUtilities.invokeLater( new Runnable()
      {
         public void run()
         {
            Client clientjeu = new Client();
            clientjeu.creerEtAffichierIug(socketJoueur_);
         }
      }
      );
    }



}