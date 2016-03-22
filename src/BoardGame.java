import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

import javax.swing.JOptionPane; //Pour les fen�tres

class Coordonnes
{
   public int PositionCaseX_ = 0;
   public int PositionCaseY_= 0;
   public int CouleurCase_ = 0;
}

class BoardGame extends JPanel implements Runnable, KeyListener, MouseListener
{
   Coordonnes PlaquetteJeu = new Coordonnes();
   Coordonnes Pion = new Coordonnes();

   private boolean PeutJouer_ = false;

   private BufferedReader auJoueur_;
   private PrintWriter versLeServeur_;

   private int maxJoueurs;
   private Socket ServeurJoueur_;
  // private Joueur joueur_;
   private String NomJoueur_ = "";
  // private BD laConnexion_;

   private Image imageTampon;
   private Graphics tampon;

   private Color couleurFond;
   private Font f1;

   private Image imageD�s;
   private String message = "";
   private char touche = ' ';

   private int sourisX;
   private int sourisY;


   public static final int MIN = 50;
   public static final int MILIEU = 300;
   public static final int MAX = 550;
   public static final int largeurRectanglesGrille = 50;
   public static final int hauteurRectanglesGrille = 50;
   public static int x;
   public static int y;

   private int cptMaxD�s = -1;



   // Si je d�clare cette variables ici... quelque chose de psych�d�lique se produit...
   //int cptCouleur = 0;


   public BoardGame(Socket socketJoueur)
   {
      try
      {
         ServeurJoueur_ = socketJoueur;
         auJoueur_ = new BufferedReader( new InputStreamReader( socketJoueur.getInputStream() ) );
         versLeServeur_ = new PrintWriter(  socketJoueur.getOutputStream(), true );

      	   //Connexion a la base de donn�es.
         /*
      	   laConnexion_ = new BD();
      	   laConnexion_.SetConnection("othotjer", "oracle", "172.17.200.251", "1521", "orcl");
      	   laConnexion_.Connecter();
      	   laConnexion_.GetConnection();
      	   //laConnexion_.AjouterJoueur(1, "Le1er", 300, 300);
      	   System.out.println(laConnexion_.GetMessage());       	    */

      	 // listeJoueur_ = listeJoueur;
         // le JPanel s'enregistre comme �couteur des �v�nements en provenance du
         // clavier et de la souris
         addKeyListener( this );
         addMouseListener( this );
         setFocusable( true );

         couleurFond = new Color( 0, 127, 0 );

         f1 = new Font( "SansSerif", Font.BOLD, 18 );

         imageD�s = Toolkit.getDefaultToolkit().getImage( "C:/Documents and Settings/200741783/Bureau/Joueur Christd�s.gif" );

         Pion.PositionCaseX_ = 300;
         Pion.PositionCaseY_ = 300;
      }
      catch (IOException ioe)
      {
         System.err.println(ioe);
      }
	}

   // exemple de cr�ation d'un faux bouton
   private void dessinerBouton( Graphics g )
   {
      g.setColor( couleurFond );
      g.draw3DRect( 730, 370, 80, 60, true );

      g.setColor( Color.RED );
      g.drawString( "START", 740, 405 );
   }

	private void DessinerPointeTarte(Graphics g )
	{
		// Affiche le nombre de pointes de tartes que le client a rapport�
      tampon.setColor( Color.WHITE );
      tampon.fillOval( 240, 0, 40, 40 );
/*
      if(Joueur.NbQuestionReussi[0] == 1)
      {
      	tampon.setColor( Color.RED );
      	tampon.fillArc( 240, 0, 40, 40, 90, 90 );
      }
      if(Joueur.NbQuestionReussi[1] == 1)
      {
        tampon.setColor( Color.GREEN );
      	tampon.fillArc( 240, 0, 40, 40, 180, 90 );
      }
      if(Joueur.NbQuestionReussi[2] == 1)
      {
      	tampon.setColor( Color.BLUE );
      	tampon.fillArc( 240, 0, 40, 40, 270, 90 );
      }
      if(Joueur.NbQuestionReussi[3] == 1)
      {
      	tampon.setColor( Color.YELLOW );
      	tampon.fillArc( 240, 0, 40, 40, 0, 90 );
      }*/
	}

   private void dessinerGrille(Graphics g)
   {
      // Dessine la grille
      int cptCouleur = 0;
      int cptCase = 0;

      for(x = MIN; x <= MAX; x = x + MIN )
      {
         for(y = MIN; y <= MAX; y = y + MIN )
         {
            if(x == MILIEU || y == MILIEU || x == MIN || x == MAX || y == MIN || y == MAX)
            {
               switch(cptCouleur)
               {
               case 0:
                  tampon.setColor( Color.BLUE );
                  tampon.fillRect( x, y, largeurRectanglesGrille, hauteurRectanglesGrille );
                  break;
               case 1:
                  tampon.setColor( Color.YELLOW);
                  tampon.fillRect( x, y, largeurRectanglesGrille, hauteurRectanglesGrille );
                  break;
               case 2:
                  tampon.setColor( Color.GREEN );
                  tampon.fillRect( x, y, largeurRectanglesGrille, hauteurRectanglesGrille );
                  break;
               case 3:
                  tampon.setColor( Color.RED );
                  tampon.fillRect( x, y, largeurRectanglesGrille, hauteurRectanglesGrille );
                  break;
               }

               if(x == MIN && y == MIN || x == MILIEU && y == MIN || x == MAX && y == MIN ||
                  x == MIN && y == MILIEU || x == MAX && y == MILIEU ||
                  x == MIN && y == MAX || x == MILIEU && y == MAX || x == MAX && y == MAX)
               {
                  switch(cptCouleur)
                  {
                  case 0:
                  tampon.setColor( Color.WHITE );
                     tampon.fillRect( x, y, largeurRectanglesGrille, hauteurRectanglesGrille );
                     tampon.setColor( Color.BLUE );
                     tampon.fillArc( x - 4, y + 12, 60, 60, 65, 50 );
                     break;
                  case 1:
                  tampon.setColor( Color.WHITE );
                     tampon.fillRect( x, y, largeurRectanglesGrille, hauteurRectanglesGrille );
                     tampon.setColor( new Color( 255, 238, 0 ));
                     tampon.fillArc( x - 4, y + 12, 60, 60, 65, 50 );
                     break;
                  case 2:
                  tampon.setColor( Color.WHITE );
                     tampon.fillRect( x, y, largeurRectanglesGrille, hauteurRectanglesGrille );
                     tampon.setColor( Color.GREEN);
                     tampon.fillArc( x - 4, y + 12, 60, 60, 65, 50 );
                     break;
                  case 3:
                     tampon.setColor( Color.WHITE );
                     tampon.fillRect( x, y, largeurRectanglesGrille, hauteurRectanglesGrille );
                     tampon.setColor( Color.RED);
                     tampon.fillArc( x - 4, y + 12, 60, 60, 65, 50 );
                     break;
                  }
               }

               // Dessine un carr� blanc au milieu de la grille
               if(x == MILIEU && y == MILIEU)
               {
                  tampon.setColor( Color.WHITE );
                  tampon.fillRect( x, y, largeurRectanglesGrille, hauteurRectanglesGrille );
               }

               // Dessine les bordures des carr�s en noir
               tampon.setColor( Color.BLACK );
               tampon.drawRect( x, y, largeurRectanglesGrille, hauteurRectanglesGrille );
               cptCouleur++;

               // Fait le retour � la premi�re couleur de choix
               if(cptCouleur == 4)
                  cptCouleur =0;
            }
            // Le reste des cases qui ne forment pas la grille sont peintur�es en vert
            else
            {
               tampon.setColor( new Color( 0, 127, 0 ));
               tampon.fillRect( x, y, largeurRectanglesGrille, hauteurRectanglesGrille );
            }

            PlaquetteJeu.PositionCaseX_ = x;
            PlaquetteJeu.PositionCaseY_ = y;
            PlaquetteJeu.CouleurCase_ = cptCouleur;
            cptCase++;
            AfficherPions(tampon);
         }
      }

   }
   public void AfficherPions(Graphics g)
   {
      tampon.fillOval(Pion.PositionCaseX_, Pion.PositionCaseY_, 25, 25  );
   }

   // cette m�thode est utilis�e pour afficher tout ce qu'il y a sur le
   // plateau de jeu
   public void paint( Graphics g )
   {
      /*
      // puisqu'on utilise la m�thode dite du double tampon, on proc�de � la
      // cr�ation de ce tampon au premier appel de paint()
      if( tampon == null )
      {
         imageTampon = createImage( 1024, 768 );
         tampon = imageTampon.getGraphics();
      }

      // nettoyage de la fen�tre
      tampon.setColor( couleurFond );
      tampon.fillRect( 0, 0, 1024, 768 );

      // Affichage des d�s
      tampon.drawImage( imageD�s, 650, 300, null );
      tampon.setColor( Color.BLACK );
            // changement de police courante
      Font f = new Font( "SansSerif", Font.BOLD + Font.ITALIC, 18 );
      tampon.setFont( f );
      tampon.drawString( "Lanc� des d�s", 730, 325 );


         //tampon.setColor(new Color((int)(Math.random() * 255) + 0, (int)(Math.random() * 255) + 0, (int)(Math.random() * 255) + 0));

      tampon.drawString( NomJoueur_, 700, 200);

      //DessinerPointeTarte(tampon);
	   //dessinerBouton( tampon );
	   tampon.drawString( message, 730, 350 );*/

         // puisqu'on utilise la m�thode dite du double tampon, on proc�de � la
      // cr�ation de ce tampon au premier appel de paint()
      if( tampon == null )
      {
         imageTampon = createImage( 1024, 768 );
         tampon = imageTampon.getGraphics();
      }

      // nettoyage de la fen�tre
      tampon.setColor( couleurFond );
      tampon.fillRect( 0, 0, 1024, 768 );

      // Affichage des d�s
      tampon.drawImage( imageD�s, 650, 300, null );
      tampon.setColor( Color.BLACK );
      Font f = new Font( "SansSerif", Font.BOLD + Font.ITALIC, 18 );
      // changement de police courante
      tampon.setFont( f );
      tampon.drawString( "Lanc� des d�s", 730, 325 );
      if(PeutJouer_)
      {
           tampon.setColor(new Color((int)(Math.random() * 255) + 0, (int)(Math.random() * 255) + 0, (int)(Math.random() * 255) + 0));
      }
      tampon.drawString( NomJoueur_, 300, 30);

      DessinerPointeTarte(tampon);
     dessinerBouton( tampon );
     tampon.drawString( message, 730, 350 );

      dessinerGrille(tampon);

       //Test la BD
      /*
      laConnexion_.RechercherQuestion(0, "");
      for(int x=0; x < 4 ;x++)
      {
         tampon.drawString(laConnexion_.liste[x], 700, (30*x)+20);
      }*/

      /*laConnexion_.AfficherJoueur();
      for(int x=0; x < 4 ;x++)
      {
         //tampon.drawString(laConnexion_.listeJoueur[x], 700, (30*x)+20);
      }*/

      // enfin on affiche le tout
        g.drawImage( imageTampon, 0, 0, null );

	}

   // c'est dans cette m�thode qu'ont lieu les �changes avec le serveur
   public void run()
   {
      boolean partieTerminee = false;
      try
      {
          String LigneLu ="?";
         //String adresse = ServeurJoueur_.getInetAddress().toString().substring(1);
         NomJoueur_ = getNomUtilisateur();
         versLeServeur_.println(NomJoueur_);
         versLeServeur_.flush();

         while( ! partieTerminee )
         {
             //Lit une ligne du serveur seulement au d�but du tour du joueur
             if(! PeutJouer_)
             {
               repaint();
               LigneLu = auJoueur_.readLine();
               PeutJouer_ = true;
               JOptionPane.showMessageDialog( null,
                                          "C'est � votre tour de jouer !",
                                          "Information au joueur",
                                          JOptionPane.WARNING_MESSAGE );
             }

             if(LigneLu.trim().equals("GO!"))
             {
                if(cptMaxD�s != 0)
                {
                  // dans certains cas une pause peut s'av�rer n�cessaire
                  Thread.sleep( 100 );
                  repaint();
                }
                else
                {
                   cptMaxD�s = -1;
                   PeutJouer_ = false;

                }

             }
         }

         /*
         //D�termine si on atteint la limite de connexions ou pas
         if( Serveur.getJoueur().size() < maxJoueurs )
         {
               PrintWriter VersLeJoueur = new PrintWriter( new OutputStreamWriter( ServeurJoueur_.getOutputStream() ), true );
               joueur_ = new Joueur(ServeurJoueur_, "");
               Serveur.AjoutJoueur( joueur_ );

               NomJoueur_ = getNomUtilisateur();

               Serveur.getJoueur().get( Serveur.getJoueur().size()-1 ).setNom( NomJoueur_ );
               VersLeJoueur.println("Salut " + NomJoueur_ + ", bienvenue sur le canal de discussion de J�r�me Othot");
               System.out.println(NomJoueur_ + "<@" + adresse + "> est en ligne.");
               // c'est ici que se d�roulent les �changes avec le serveur selon le
               // protocole que j'ai d�velopp�

               // par exemple, je viens de recevoir un message indiquant qu'un joueur
               // est rendu � la case 0,8

               // je mets donc � jour sa position dans ma structure de donn�es et je
               // commande un r�affichage

               // ...
               while( ! partieTerminee )
               {
                  // dans certains cas une pause peut s'av�rer n�cessaire
                  Thread.sleep( 100 );
                  repaint();
               }
         }
         else
         {
             JOptionPane.showMessageDialog( null,
                                            "ATTENTION ! Nombre de client maximal atteint. LE SERVEUR va se fermer. !",
                                            "Nom invalide",
                                            JOptionPane.WARNING_MESSAGE );
            //throw new InterruptedException();
         }
         ServeurJoueur_.close();
         */
      }
      catch( InterruptedException ie )
      {
      }
      catch(Exception ioe)
      {
         System.err.println("Probleme de lecture et d'ecriture lors de la discusion du client.");
         System.err.println(ioe);
      }
   }

   // gestion des �v�nements du clavier
   public void keyTyped( KeyEvent e )
   {

   }


   public void keyPressed( KeyEvent e )
   {
      if(PeutJouer_)
      {
         //touche = e.getKeyChar();
         //tampon.drawString( message, 730, 350 );
         cptMaxD�s = Integer.parseInt(message);
         System.out.println(cptMaxD�s);

         if(e.getKeyCode() == e.VK_RIGHT && Pion.PositionCaseX_ < MAX && (Pion.PositionCaseY_ == MIN ||
                 Pion.PositionCaseY_ == MILIEU || Pion.PositionCaseY_ == MAX) && cptMaxD�s > 0)
         {
                Pion.PositionCaseX_ += 50;
                cptMaxD�s--;
         }
         else if(e.getKeyCode() == e.VK_LEFT && Pion.PositionCaseX_ > MIN && (Pion.PositionCaseY_ == MIN ||
                 Pion.PositionCaseY_ == MILIEU || Pion.PositionCaseY_ == MAX) && cptMaxD�s > 0)

         {
              Pion.PositionCaseX_ -= 50;
               cptMaxD�s--;
         }
         else if(e.getKeyCode() == e.VK_DOWN && Pion.PositionCaseY_ < MAX && (Pion.PositionCaseX_ == MIN ||
                     Pion.PositionCaseX_ == MILIEU || Pion.PositionCaseX_ == MAX) && cptMaxD�s > 0)
         {
            Pion.PositionCaseY_ += 50;
            cptMaxD�s--;
         }
         else if(e.getKeyCode() == e.VK_UP && Pion.PositionCaseY_ > MIN &&(Pion.PositionCaseX_ == MIN ||
                  Pion.PositionCaseX_ == MILIEU || Pion.PositionCaseX_ == MAX) && cptMaxD�s > 0)
         {
            Pion.PositionCaseY_ -= 50;
            cptMaxD�s--;
         }
         message = Integer.toString(cptMaxD�s);
      }
      else
      {
         JOptionPane.showMessageDialog( null,
                                          "Ce n'est pas � votre tour de jouer !",
                                          "Information au joueur",
                                          JOptionPane.WARNING_MESSAGE );
      }
   }

   public void keyReleased( KeyEvent e )
   {
   }

   // gestion des �v�nemnts de la souris
   public void mouseClicked( MouseEvent e )
   {
   }

   public void mouseEntered( MouseEvent e )
   {
   }

   public void mouseExited( MouseEvent e )
   {
   }

   public void mousePressed( MouseEvent e )
   {
      sourisX = e.getX();
      sourisY = e.getY();

      if( ( sourisX >= 730 ) && ( sourisX <= 800 ) && ( sourisY >= 370 ) && ( sourisY <= 440 ) )
      {
         //message = "Ya man!";
         //message = Integer.toString((int)(Math.random() * 6) + 1);
         if(PeutJouer_)
         {
            message = Integer.toString((int)(Math.random() * 6) + 1);
            cptMaxD�s = Integer.parseInt(message);
         }
         else
         {
            JOptionPane.showMessageDialog( null,
                                          "Ce n'est pas � votre tour de jouer !",
                                          "Information au joueur",
                                          JOptionPane.WARNING_MESSAGE );
         }
      }

   }

   public void mouseReleased( MouseEvent e )
   {
   }

   //Permet � l'utilisateur de rentrer son nom d'utilisateur
   private String getNomUtilisateur()
   {
      String nomClient = "";
      boolean recommence = false;

         do
         {
            nomClient = JOptionPane.showInputDialog( null, "Entrez votre nom :" );

            //D�termine si le client � rentrer un nom
            if( nomClient.trim().equals("") || nomClient == null  )
            {
               JOptionPane.showMessageDialog( null,
                                                 "Veuillez S.V.P. entrer un nom de joueur !",
                                                 "Nom invalide",
                                                 JOptionPane.ERROR_MESSAGE );
               recommence = true;
            }
            else
            {
               //D�termine si le nom renter par l'utilisateur existe d�ja
               if( nomUtiliser(nomClient) )
               {
                  JOptionPane.showMessageDialog( null,
                                                 "OUPS ! Votre nom de joueur est d�ja utiliser par un autre client. Veuillez entrez un nouveau nom !",
                                                 "Nom invalide",
                                                 JOptionPane.ERROR_MESSAGE );
                  recommence = true;
               }
               else
                  recommence = false;
            }
         }
         while( recommence );

      return nomClient;
   }

   //D�termine si le nom entrer par l'utilisateur est d�ja utiliser par un autre client
   private boolean nomUtiliser(String nom)
   {
      boolean DejaUtiliser = false;
      /*
      for( int i = 0; i < Serveur.getJoueur().size(); ++i)
      {
         if( Serveur.getJoueur().get(i).getNomJoueur().equals(nom) )
            DejaUtiliser = true;
      }*/
      return DejaUtiliser;
   }
}