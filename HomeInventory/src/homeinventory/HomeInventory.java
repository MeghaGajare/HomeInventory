package homeinventory;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.GridBagConstraints;
import com.toedter.calendar.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.text.*;
import java.awt.print.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
//import java.beans.*;
import java.io.*;
public class HomeInventory 
{
		JLabel Lin_item,Lloc,Lserial_no,Lpurch_price,Ldate_purch,Lstore_web,Lnote,Lphoto;
	    JTextField Tin_item,Tloc,Tserial_no,Tpurch_price,Tdate_purch,Tstore_web,Tnote;
		static JTextField Tphoto; 
	    JButton Bnew,Bdelete,Bsave,Bprevious,Bnext,Bprint,Bexit,Bphoto;
	    InventoryItem myItem;
	    JToolBar Tbinventory;
	    JCheckBox Cmarked;
	    JPanel searchPanel;
	    JComboBox<String> Cloc;
	    JFileChooser openChooser;
	    JDateChooser dateDateChooser;
	    JButton[] searchButton=new JButton[26];
	    final static int maximumEntries = 300;
		static int numberEntries;
		static final int entriesPerPage=2;
		static int lastPage;
		static InventoryItem[] myInventory = new InventoryItem[maximumEntries];
	    GridBagConstraints gridConstraints;
	    PhotoPanel1 photoPanel =new PhotoPanel1();
	    int currentEntry;
	    
		public HomeInventory()
		{		
			JFrame f=new JFrame("Home Inventory Manager");
			
			//Label
			Lin_item =new JLabel("Inventory Item: ");
			Lin_item.setBounds(140,15,100,25);
			Lloc =new JLabel("Location: "); 
			Lloc.setBounds(140,50,100,25);
			Lserial_no =new JLabel("Serial Number: "); 
			Lserial_no.setBounds(140,85,100,25);
			Lpurch_price =new JLabel("Purchase Price: "); 
			Lpurch_price.setBounds(140,120,100,25);
			Ldate_purch =new JLabel("Date Purchase: "); 
			Ldate_purch.setBounds(450,120,100,25);
			Lstore_web =new JLabel("Store/Website: "); 
			Lstore_web.setBounds(140,155,100,25);
			Lnote =new JLabel("Note: "); 
			Lnote.setBounds(140,190,100,25);
			Lphoto =new JLabel("Photo: "); 
			Lphoto.setBounds(140,225,100,25);
			
			searchPanel=new JPanel();
			searchPanel.setBounds(140,290,240,160);
			searchPanel.setBorder(BorderFactory.createTitledBorder("Item Search"));
			searchPanel.setLayout(new GridBagLayout());
			gridConstraints =new GridBagConstraints();
			gridConstraints.gridx = 1;
			gridConstraints.gridy = 7;
			gridConstraints.gridwidth = 3; 
			gridConstraints.insets = new Insets(10,0,10,0);
			gridConstraints.anchor = GridBagConstraints.CENTER;
	    	Container container =new Container();
			container.add(searchPanel,gridConstraints);

			int x=0,y=0;
			//create and position 26 buttons
			for(int i=0;i<26;i++)
			{
				//create new button
				    searchButton[i] =new JButton();
					searchButton[i].setText(String.valueOf((char)(65+i)));
					searchButton[i].setFont(new Font("Arial", Font.BOLD, 12));
					searchButton[i].setBounds(145,300,25,25);
					searchButton[i].setMargin(new Insets(-10,-10,-10,-10));
					sizeButton(searchButton[i], new Dimension(37, 27)); 
					searchButton[i].setBackground(Color.YELLOW);
					searchButton[i].setFocusable(false);
					gridConstraints =new GridBagConstraints();
					gridConstraints.gridx = x;
					gridConstraints.gridy = y;
					searchPanel.add(searchButton[i],gridConstraints);
					//add method
					searchButton[i].addActionListener(new bsearch());
					x++;
					//six button per row
					if(x % 6 == 0)
					{
						x=0;
						y++;
					}
			}
			
		    photoPanel.setBounds(400,280,300,220);

			//Combobox
			String country[]= {" Furnace Room","Under my desk","Always Lost","Basement Office","Family Room","My Office","Under the other desk"};
			Cloc =new JComboBox<>(country);
			Cloc.setBounds(245,50,300,25);
			Cloc.setFont(new Font("Arial",Font.PLAIN,12));
			Cloc.setEditable(true);
			Cloc.setBackground(Color.WHITE);
			
			//checkBox
			Cmarked =new JCheckBox();
			Cmarked.setBounds(555,50,75,25);
			Cmarked.setText("Marked?");
			Cmarked.setFocusable(false);
			
			//textfield
			Tin_item  =new JTextField();
			Tin_item.setBounds(245,15,450,25);
			Tloc =new JTextField();
			Tserial_no=new JTextField();
			Tserial_no.setBounds(245,85,300,25);
			Tpurch_price=new JTextField();
			Tpurch_price.setBounds(245,120,190,25);
			
			dateDateChooser = new JDateChooser();
			dateDateChooser.setBounds(540,120,155,25);
			
			Tstore_web=new JTextField();
			Tstore_web.setBounds(245,155,450,25);
			Tnote=new JTextField();
			Tnote.setBounds(245,190,450,25);
			Tphoto=new JTextField();
			Tphoto.setBounds(245,225,400,35);
			Tphoto.setEditable(false);
			Tphoto.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			Tphoto.setBackground(new Color(255, 255, 192));
			Tphoto.setFocusable(false);
			
			//Button
			Tbinventory=new JToolBar();	
			Tbinventory.setFloatable(false);
			Tbinventory.setBackground(Color.BLUE);
			Tbinventory.setOrientation(SwingConstants.VERTICAL);
			Tbinventory.addSeparator();
			
			
			Bnew=new JButton( new ImageIcon("C:\\Users\\Pawan\\Desktop\\megha\\HomeInventory\\src\\new.gif"));
			Bnew.setBounds(10,10,100,60);
			Bnew.setText("New");
			Bnew.setToolTipText("Add New Item");
			Bnew.setHorizontalTextPosition(SwingConstants.CENTER);
			Bnew.setVerticalTextPosition(SwingConstants.BOTTOM);
			Tbinventory.add(Bnew);
						
			Bdelete=new JButton(new ImageIcon("C:\\Users\\Pawan\\Desktop\\megha\\HomeInventory\\src\\delete.gif"));
			Bdelete.setBounds(10,75,100,60);
			Bdelete.setText("Delete");
			Bdelete.setToolTipText("Delete Current Item");
			Bdelete.setHorizontalTextPosition(SwingConstants.CENTER);
			Bdelete.setVerticalTextPosition(SwingConstants.BOTTOM);
			Bdelete.setFocusable(false);
			Tbinventory.add(Bdelete);
			
			Bsave=new JButton(new ImageIcon("C:\\Users\\Pawan\\Desktop\\megha\\HomeInventory\\src\\save.gif"));
			Bsave.setBounds(10,140,100,60);
			Bsave.setText("Save");
			Bsave.setToolTipText("Save Current Item");
			Bsave.setHorizontalTextPosition(SwingConstants.CENTER);
			Bsave.setVerticalTextPosition(SwingConstants.BOTTOM);
			Bsave.setFocusable(false);
			Tbinventory.add(Bsave);
			Tbinventory.addSeparator();
			
			Bprevious=new JButton(new ImageIcon("C:\\Users\\Pawan\\Desktop\\megha\\HomeInventory\\src\\previous.gif"));
			Bprevious.setBounds(10,220,100,60);
			Bprevious.setText("Previous");
			Bprevious.setToolTipText("Display Previous Item");
			Bprevious.setHorizontalTextPosition(SwingConstants.CENTER);
			Bprevious.setVerticalTextPosition(SwingConstants.BOTTOM);
			Bprevious.setFocusable(false);
			Tbinventory.add(Bprevious);
			
			Bnext=new JButton(new ImageIcon("C:\\Users\\Pawan\\Desktop\\megha\\HomeInventory\\src\\next.gif"));
			Bnext.setBounds(10,285,100,60);
			Bnext.setText("Next");
			Bnext.setToolTipText("Display Next Item");
			Bnext.setHorizontalTextPosition(SwingConstants.CENTER);
			Bnext.setVerticalTextPosition(SwingConstants.BOTTOM);
			Bnext.setFocusable(false);
			Tbinventory.add(Bnext);
			Tbinventory.addSeparator();
			
			
			Bprint=new JButton(new ImageIcon("C:\\Users\\Pawan\\Desktop\\megha\\HomeInventory\\src\\print.gif"));
			Bprint.setBounds(10,365,100,60);
			Bprint.setText("Print");
			Bprint.setToolTipText("Print Inventory List");
			Bprint.setHorizontalTextPosition(SwingConstants.CENTER);
			Bprint.setVerticalTextPosition(SwingConstants.BOTTOM);
			Bprint.setFocusable(false);
			Tbinventory.add(Bprint);		
			
			Bexit=new JButton("Exit");
			Bexit.setBounds(10,430,100,60);
			Bexit.setToolTipText("Exit Program");
			Bexit.setHorizontalTextPosition(SwingConstants.CENTER);
			Bexit.setVerticalTextPosition(SwingConstants.BOTTOM);
			Bexit.setFocusable(false);
			Tbinventory.add(Bexit);
			
			
			Bphoto =new JButton("...");
			Bphoto.setBounds(655,225,30,25);		
			
			f.add(searchPanel);
			f.add(Lin_item);
			f.add(Lloc);
			f.add(Lserial_no);
			f.add(Lpurch_price);
			f.add(Ldate_purch);
			f.add(Lstore_web);
			f.add(Lnote);
			f.add(Lphoto);
			f.add(searchPanel);
			f.add(photoPanel);
			
			f.add(Cmarked);
			f.add(Cloc);
			Cloc.addActionListener(new cloc());
			
			f.add(Tin_item);
			Tin_item.addActionListener(new tin_item());
			f.add(Tserial_no);
			Tserial_no.addActionListener(new tserial_no());
			f.add(Tpurch_price);
			Tpurch_price.addActionListener(new tpurch_price());
			f.add(dateDateChooser);
			dateDateChooser.addPropertyChangeListener(new tdate_purch());
			
			f.add(Tstore_web);
			Tstore_web.addActionListener(new tstore_web());
			f.add(Tnote);
			Tnote.addActionListener(new tnote());
			f.add(Tphoto);
			Tphoto.addActionListener(new tphoto());
			f.add(Bphoto);
			Bphoto.addActionListener(new bphoto());
			f.add(Bnew);
			Bnew.addActionListener(new bnew());
			f.add(Bdelete);
			Bdelete.addActionListener(new bdelete());
			f.add(Bsave);
			Bsave.addActionListener(new bsave());
			f.add(Bprevious);
			Bprevious.addActionListener(new bprevious());
			f.add(Bnext);
			Bnext.addActionListener(new bnext());
			f.add(Bprint);
			Bprint.addActionListener(new bprint());
			f.add(Bexit);
			Bexit.addActionListener(new bexit());
			
			int n;
			//open file for entries try
			try
			{
				BufferedReader inputFile=null;
				inputFile = new BufferedReader(new FileReader("C:\\Users\\Pawan\\Desktop\\megha\\HomeInventory\\inventory.txt"));	
				numberEntries = Integer.valueOf(inputFile.readLine()).intValue();
			    
			    if(numberEntries !=0)
			    {
			    	for(int i=0; i<numberEntries;i++)
			    	{
			    		myInventory[i] = new InventoryItem();
						myInventory[i].description=inputFile.readLine();					
			    		myInventory[i].location=inputFile.readLine();
			    		myInventory[i].serialNumber=inputFile.readLine();
			    		myInventory[i].marked= Boolean.valueOf(inputFile.readLine()).booleanValue();
			    		myInventory[i].purchasePrice= inputFile.readLine();
			    		myInventory[i].purchaseDate= inputFile.readLine();
			    		myInventory[i].purchaseLocation= inputFile.readLine();
			    		myInventory[i].note = inputFile.readLine();
			    		myInventory[i].photoFile=inputFile.readLine();
			    	}
			    }
			    //read in combo box elements
			    n=Integer.valueOf(inputFile.readLine()).intValue();
			    if(n!=0)
			    {
			    	for(int i=0; i<n ; i++)
			    	{
			    		Cloc.addItem(inputFile.readLine());
			    	}
			    }
			    inputFile.close();
			    currentEntry=1;
			    showEntry(currentEntry);
			    
			}
			catch(Exception ex)
			{
				numberEntries = 0;
				currentEntry = 0;
			}
			if(numberEntries==0)
			{
				Bnew.setEnabled(false);
				Bdelete.setEnabled(false);
				Bnext.setEnabled(false);
				Bprevious.setEnabled(false);
				Bprint.setEnabled(false);
			}
			
			f.setSize(750,550);
	    	f.setLayout(null);
	    	f.setVisible(true);
	    	f.setResizable(false);
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new HomeInventory();
	}
	
	private void sizeButton(JButton b, Dimension d) 
	{
		b.setPreferredSize(d);
		b.setMinimumSize(d);
		b.setMaximumSize(d);

	}
	
	//search button
	public class bsearch implements ActionListener
	{
  	public void actionPerformed(ActionEvent e)
	     {
		       searchButtonActionPerformed(e);  
	     }  
	
	}
	private void searchButtonActionPerformed(ActionEvent e)
	{
		int i;
		if(numberEntries==0)
			return;
		//search for item letter
		String letterClicked = e.getActionCommand();
		i=0;
		do
		{
			if(myInventory[i].description.substring(0,1).equals(letterClicked))
			{
				currentEntry=i+1;
				showEntry(currentEntry);
				return;
			}
			i++;
		}while(i<numberEntries);
		    JOptionPane.showConfirmDialog(null, "NO "+letterClicked+" inventory item.","None Found",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	//photo button
	public class bphoto implements ActionListener
	{
  	public void actionPerformed(ActionEvent e)
	     {
		       BphotoActionPerformed(e);  
	     }  
	
	}
	private void BphotoActionPerformed(ActionEvent e)
	{
		JFileChooser openChooser = new JFileChooser("");
		openChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		openChooser.setDialogTitle("Open Photo File");
		openChooser.addChoosableFileFilter(new FileNameExtensionFilter("Photo Files","jpg"));
		int a =openChooser.showOpenDialog(null);
		if(a==JFileChooser.APPROVE_OPTION)
			showPhoto(openChooser.getSelectedFile().toString());
	}
	//new
		public class bnew implements ActionListener
		{
	    	public void actionPerformed(ActionEvent e)
		     {
			       BnewActionPerformed(e);  
		     }  
		}
		private void BnewActionPerformed(ActionEvent e)
		{
			checkSave();
			blankValues();
		}
		
		
		
		//delete
		public class bdelete implements ActionListener
		{
	    	public void actionPerformed(ActionEvent e)
		     {
			       BdeleteActionPerformed(e);  
		     }  
		
		}
		private void BdeleteActionPerformed(ActionEvent e)
		{
			if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?","Delete Inventory Item",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION)
				 return;
			deleteEntry(currentEntry);
			if(numberEntries==0)
			{
				currentEntry = 0;
				blankValues();
			}
			else
			{
				currentEntry--;
				if(currentEntry==0)
					currentEntry=1;
				showEntry(currentEntry);
			}
		}
		
		//save
		public class bsave implements ActionListener
		{
	    	public void actionPerformed(ActionEvent e)
		     {
			       BsaveActionPerformed(e);  
		     }  
		
		}
		private void BsaveActionPerformed(ActionEvent e)
		{
			//check for desription
			Tin_item.setText(Tin_item.getText().trim());
			if(Tin_item.getText().equals(""))
			{
				JOptionPane.showConfirmDialog(null, "Must have item description","Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
				Tin_item.requestFocus();
				return;
			}
			if(Bnew.isEnabled())
			{
				//delete edit entry then resave
				deleteEntry(currentEntry);
			}
			// capitalize first letter
			String s = Tin_item.getText();
			Tin_item.setText(s.substring(0,1).toUpperCase()+s.substring(1));
			numberEntries++;
			//determine new current entry location based on description
			currentEntry=1;
			if(numberEntries !=1)
			{
				do
				{
					if(Tin_item.getText().compareTo(myInventory[currentEntry-1].description)<0)
			              break;	
					currentEntry++;    
				}while(currentEntry<numberEntries);
			}
			//move all entries below new value down one position unless at end
			if(currentEntry!=numberEntries)
			{
				for(int i = numberEntries; i>=currentEntry+1;i--)
				{
					myInventory[i-1] = myInventory[i-2];
					myInventory[i-2] = new InventoryItem();
				}
			}
			myInventory[currentEntry-1]=new InventoryItem();
			myInventory[currentEntry-1].description=Tin_item.getText();
			myInventory[currentEntry-1].location= Cloc.getSelectedItem().toString();
			myInventory[currentEntry-1].marked=Cmarked.isSelected();
			myInventory[currentEntry-1].serialNumber=Tserial_no.getText();
			myInventory[currentEntry-1].purchasePrice=Tpurch_price.getText();
			myInventory[currentEntry-1].purchaseDate=dateToString(dateDateChooser.getDate());
			myInventory[currentEntry-1].purchaseLocation=Tstore_web.getText();
			myInventory[currentEntry-1].photoFile=Tphoto.getText();
			myInventory[currentEntry-1].note=Tnote.getText();
			showEntry(currentEntry);
			if(numberEntries<maximumEntries)
				Bnew.setEnabled(true);
			else
				Bnew.setEnabled(false);
			Bdelete.setEnabled(true);
			Bprint.setEnabled(true);
		}
		
		
		//previous
		public class bprevious implements ActionListener
		{
	    	public void actionPerformed(ActionEvent e)
		     {
			       BpreviousActionPerformed(e);  
		     }  
		
		}
		private void BpreviousActionPerformed(ActionEvent e)
		{
			checkSave();
			currentEntry--;
			showEntry(currentEntry);
		}
		
		
		//next
		public class bnext implements ActionListener
		{
	    	public void actionPerformed(ActionEvent e)
		     {
			       BnextActionPerformed(e);  
		     }  
		
		}
		private void BnextActionPerformed(ActionEvent e)
		{
			checkSave();
			currentEntry++;
			showEntry(currentEntry);
		}
		
		//print
		public class bprint implements ActionListener
		{
	    	public void actionPerformed(ActionEvent e)
		     {
			       BprintActionPerformed(e);  
		     }  
		}
		private void BprintActionPerformed(ActionEvent e)
		{
			lastPage = (int)(1+(numberEntries-1)/entriesPerPage);
			PrinterJob inPrinterJob= PrinterJob.getPrinterJob();
			inPrinterJob.setPrintable(new InventoryDocument());
			if(inPrinterJob.printDialog())
			{
				try
				{
					inPrinterJob.print();
				}
				catch(PrinterException ex)
				{
					JOptionPane.showConfirmDialog(null, ex.getMessage(),"Print Error",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		//exit
		public class bexit implements ActionListener
		{
	    	public void actionPerformed(ActionEvent e)
		     {
			       BexitActionPerformed(e);  
		     }  
		
		}
		private void BexitActionPerformed(ActionEvent e)
		{
			exitForm(null);
		
		}
		
		
		//
		public class tin_item implements ActionListener   //
		{
			public void actionPerformed(ActionEvent e)
		     {
			       Tin_itemActionPerformed(e);  
		     }  
		}
		private void Tin_itemActionPerformed(ActionEvent e)
		{
			Cloc.requestFocus();
		}
		
		
		//
		public class tloc implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
		     {
			       TlocActionPerformed(e);  
		     }  

		}
		private void TlocActionPerformed(ActionEvent e)
		{
			Tloc.requestFocus();
		}
		//
		public class tserial_no implements ActionListener  //
		{
			public void actionPerformed(ActionEvent e)
		     {
			       Tserial_noActionPerformed(e);  
		     }  

		}
		private void Tserial_noActionPerformed(ActionEvent e)
		{
			Tpurch_price.requestFocus();
		}
		
		//
		public class tpurch_price implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
		     {
			       Tpurch_priceActionPerformed(e);  
		     }  

		}
		private void Tpurch_priceActionPerformed(ActionEvent e)
		{
			dateDateChooser.requestFocus();   //remain                        
		}
		//
		public class tdate_purch implements PropertyChangeListener
		{
			public void propertyChange(PropertyChangeEvent e)
		     {
			       dateDateChooserPropertyChange(e);  
		     }  
		}
		private void dateDateChooserPropertyChange(PropertyChangeEvent e)
		{
			Tstore_web.requestFocus();
		}
		//
		public class tstore_web implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
		     {
			       Tstore_webActionPerformed(e);  
		     }  

		}
		private void Tstore_webActionPerformed(ActionEvent e)
		{
			Tnote.requestFocus();
		}
		//
		public class tnote implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
		     {
			       TnoteActionPerformed(e);  
		     }  
		}
		private void TnoteActionPerformed(ActionEvent e)
		{
			Bphoto.requestFocus();
		}
		//
		public class tphoto implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
		     {
			       TphotoActionPerformed(e);  
		     }  

		}
		private void TphotoActionPerformed(ActionEvent e)
		{
			Tphoto.requestFocus();
		}
		//
		public class cloc implements ActionListener 
		{
			public void actionPerformed(ActionEvent e)
		     {
			       ClocActionPerformed(e);  
		     }
		}
		private void ClocActionPerformed(ActionEvent e)
		{
			// in list - exit method
			if(Cloc.getItemCount()!=0)
			{
				for(int i=0;i<Cloc.getItemCount();i++)
				{
				if(Cloc.getSelectedItem().toString().equals(Cloc.getItemAt(i).toString()))
					{
						Tserial_no.requestFocus();
						return;
					}
				}
			}
			//If not found,add to list boc
			Tserial_no.requestFocus();
		}
		
		private void checkSave()
		{
			boolean edited=false;
			if(!myInventory[currentEntry-1].description.equals(Tin_item.getText()))
				edited=true;
			else if(!myInventory[currentEntry-1].location.equals(Cloc.getSelectedItem().toString()))
				edited = true;
			else if(myInventory[currentEntry-1].marked!=Cmarked.isSelected())
				edited = true;
			else if(!myInventory[currentEntry-1].serialNumber.equals(Tserial_no.getText()))
				edited = true;
			else if(!myInventory[currentEntry-1].purchasePrice.equals(Tpurch_price.getText()))
				edited = true;
			else if(!myInventory[currentEntry-1].purchaseDate.equals(dateToString(dateDateChooser.getDate())))
				edited = true;
			else if(!myInventory[currentEntry-1].note.equals(Tnote.getText()))
				edited = true;
			else if(!myInventory[currentEntry-1].photoFile.equals(Tphoto.getText()))
				edited = true;
			if(edited)
			{
				if(JOptionPane.showConfirmDialog(null,"You have edited this item.Do you want to save the change?","Save Item",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
					Bsave.doClick();
			}			
		}
		
		
		private void deleteEntry(int j)
		{
			//delete entry j
			if(j!=numberEntries)
			{
				//move all entries under j up one level
				for(int i=j;i<numberEntries;i++)
				{
					myInventory[i-1]=new InventoryItem();
					myInventory[i-1]=myInventory[i];
				}
			}
			numberEntries--;
		}
		
		private void showEntry(int j)
		{ // display entry j (1 to numberEntries) 
			Tin_item.setText(myInventory[j - 1].description);
			Cloc.setSelectedItem(myInventory[j - 1].location);
			Cmarked.setSelected(myInventory[j - 1].marked); 
	        Tserial_no.setText(myInventory[j - 1].serialNumber); 
			Tpurch_price.setText(myInventory[j - 1].purchasePrice);
			dateDateChooser.setDate(stringToDate(myInventory[j - 1].purchaseDate)); 
			Tstore_web.setText(myInventory[j - 1].purchaseLocation); 
			Tnote.setText(myInventory[j - 1].note); 
			showPhoto(myInventory[j - 1].photoFile); 
			Bnext.setEnabled(true);
			Bprevious.setEnabled(true);
			if(j==1)		
				Bprevious.setEnabled(false);
			if(j==numberEntries)
				Bnext.setEnabled(false);		
			Tin_item.requestFocus(); 
			
		}
		private void showPhoto(String photoFile)
		{
			if (!photoFile.equals(""))
			{
				try
				{
					Tphoto.setText(photoFile); 
		        }		    
				catch (Exception ex)
				{
					Tphoto.setText(""); 
				}
			} 
			else 
			{ 
				Tphoto.setText("");
			}
			photoPanel.repaint();
		}
		private Date stringToDate(String s) 
		{
			int m = Integer.valueOf(s.substring(0, 2)).intValue() - 1; 
			int d = Integer.valueOf(s.substring(3, 5)).intValue();
		    int y = Integer.valueOf(s.substring(6)).intValue() - 1900; 
		    return(new Date(y, m, d));
		}
     
		private String dateToString(Date dd)
		{
			String yString = String.valueOf(dd.getYear() + 1900);
			int m = dd.getMonth() + 1;
			String mString = new DecimalFormat("00").format(m);
			int d = dd.getDate(); 
			String dString = new DecimalFormat("00").format(d);
			return(mString + "/" + dString + "/" + yString);
		}

		
		private void exitForm(WindowEvent evt)
		{
			if(JOptionPane.showConfirmDialog(null,"Any unsaved changes will be lost.\n Are you sure you want to exit?","Exit Program",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.NO_OPTION)
				 return;   
			// write entries back to file
			try
			{
				PrintWriter outputFile = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\Pawan\\Desktop\\megha\\HomeInventory\\inventory.txt")));
				outputFile.println(numberEntries);

				if (numberEntries != 0)
				{ 
					for (int i = 0; i < numberEntries; i++)
					{
						outputFile.println(myInventory[i].description); 
						outputFile.println(myInventory[i].location);
						outputFile.println(myInventory[i].serialNumber);
						outputFile.println(myInventory[i].marked);
						outputFile.println(myInventory[i].purchasePrice);
						outputFile.println(myInventory[i].purchaseDate);
						outputFile.println(myInventory[i].purchaseLocation);
						outputFile.println(myInventory[i].note);
						outputFile.println(myInventory[i].photoFile); 
					}
				} 
		// write combo box entries
				outputFile.println(Cloc.getItemCount());
				if (Cloc.getItemCount() != 0) 
				{ 
					for (int i = 0; i < Cloc.getItemCount(); i++)
						outputFile.println(Cloc.getItemAt(i)); 
				}
				outputFile.close();
			} 
			catch (Exception ex)
			{
			}
			System.exit(0);
		}
		private void blankValues() {
			// blank input screen
			Bnew.setEnabled(false); 
			Bdelete.setEnabled(false); 
			Bsave.setEnabled(true); 
			Bprevious.setEnabled(false); 
			Bnext.setEnabled(false);
			Bprint.setEnabled(false); 
			Tin_item.setText(""); 
			Cloc.setSelectedItem("");
			Cmarked.setSelected(false);
			Tserial_no.setText(""); 
			Tpurch_price.setText("");
			dateDateChooser.setDate(new Date());
			Tstore_web.setText(""); 
			Tnote.setText(""); 
			Tphoto.setText(""); 
			photoPanel.repaint();
			Tin_item.requestFocus();  
		}

}

class InventoryDocument implements Printable
{
   public int print(Graphics g, PageFormat pf, int pageIndex)
   {
	   Graphics2D g2D = (Graphics2D)g;
	   if((pageIndex + 1)>HomeInventory.lastPage)
	   {
		   return NO_SUCH_PAGE;
	   }
	   int i,iEnd;
	   //here you decide what goes on each page and draw it
	   //header
	   g2D.setFont(new Font("Arial",Font.BOLD,14));
	   g2D.drawString("Home Inventory Items - Page"+String.valueOf(pageIndex+1),(int) pf.getImageableX(),(int)(pf.getImageableY()+25));
	   int dy=(int)g2D.getFont().getStringBounds("s",g2D.getFontRenderContext()).getHeight();
	   int y =(int)(pf.getImageableY()+4*dy);
	   iEnd=HomeInventory.entriesPerPage*(pageIndex+1);
	   if(iEnd>HomeInventory.numberEntries)
		   iEnd=HomeInventory.numberEntries;
	   for(i=0+HomeInventory.entriesPerPage*pageIndex;i<iEnd;i++)
	   {
		   //dividing line
		   Line2D.Double dividingLine=new Line2D.Double(pf.getImageableX(),y,pf.getImageableX()+pf.getImageableWidth(),y);
		   g2D.draw(dividingLine);
		   y+=dy;
		   g2D.setFont(new Font("Arial",Font.BOLD,12));
		   g2D.drawString(HomeInventory.myInventory[i].description,(int)pf.getImageableX(),y);
		   y+=dy;
		   g2D.setFont(new Font("Arial",Font.PLAIN,12));
		   g2D.drawString("Location: "  +HomeInventory.myInventory[i].location,(int) (pf.getImageableX()+25), y);
		   y+=dy;
		   if(HomeInventory.myInventory[i].marked)
			   g2D.drawString("Item is marked with identifying information.", (int)(pf.getImageableX()+25), y);
		   else
			   g2D.drawString("Item is NOT marked with identifying information,", (int)(pf.getImageableX()+25), y);
		   y+=dy;
		   g2D.drawString("Serial Number: "+HomeInventory.myInventory[i].serialNumber,(int)(pf.getImageableX()+25), y);
		   y+=dy;
		   g2D.drawString("Price: $"+HomeInventory.myInventory[i].purchasePrice+"Purchased on:"+HomeInventory.myInventory[i].purchaseDate, (int)(pf.getImageableX()+25), y);
		   y+=dy;
		   g2D.drawString("Purchased at: "+HomeInventory.myInventory[i].purchaseLocation,(int)(pf.getImageableX()+25),y);
		   y+=dy;
		   g2D.drawString("Note: "+HomeInventory.myInventory[i].note,(int) (pf.getImageableX()+25), y);
		   y+=dy;
		   try 
		   {
			   //maintain original width/height ratio
			   
			   Image inventoryImage = new ImageIcon(HomeInventory.myInventory[i].photoFile).getImage();
			   double ratio = (double)(inventoryImage.getWidth(null))/(double)inventoryImage.getHeight(null);
			   g2D.drawImage(inventoryImage,(int)(pf.getImageableX()+25),y,(int)(100*ratio),100,null);
			   
		   }
		   catch(Exception ex)
		   {
			   //hava place to go in case image file doesnt open
		   }
		   y+=2*dy+100;
	   }
	   return PAGE_EXISTS;
	   
   }
}



@SuppressWarnings("serial")
class PhotoPanel1 extends JPanel
{
	public void paintComponent(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		super.paintComponent(g2D);
		
		// draw border
		g2D.setPaint(Color.BLACK);
		g2D.draw(new Rectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1));
		
		//show photo
		Image photoImage = new ImageIcon(HomeInventory.Tphoto.getText()).getImage();
		int w = getWidth();
		int h = getHeight();
		double rWidth = (double)getWidth()/(double)photoImage.getWidth(null);
		double rHeight= (double)getHeight()/(double)photoImage.getHeight(null);
		if(rWidth>rHeight)
		{
			//leave height at display height,change width by amount height is changed
			w=(int)(photoImage.getWidth(null)*rHeight);
		}
		else
		{
			//leave width at display width,change height by amount width is changed
			h=(int)(photoImage.getHeight(null)*rWidth);
			
		}
		//center in panel
		g2D.drawImage(photoImage,(int)(0.5*(getWidth()-w)),(int)(0.5*(getHeight()-h)),w,h,null);
		g2D.dispose();
	}
}
