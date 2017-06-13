/**
 * 
 */
/**
 * @author Administrator
 *
 */
package netbook;
import java.awt.CheckboxMenuItem;  
import java.awt.Color;  
import java.awt.Container;  
import java.awt.FileDialog;  
import java.awt.FlowLayout;  
import java.awt.Font;  
import java.awt.GraphicsEnvironment;  
import java.awt.Menu;  
import java.awt.MenuBar;  
import java.awt.MenuItem;  
import java.awt.MenuShortcut;  
import java.awt.TextArea;  
import java.awt.Toolkit;  
import java.awt.Window;  
import java.awt.datatransfer.Clipboard;  
import java.awt.datatransfer.DataFlavor;  
import java.awt.datatransfer.StringSelection;  
import java.awt.datatransfer.Transferable;  
import java.awt.datatransfer.UnsupportedFlavorException;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.KeyEvent;  
import java.awt.event.WindowAdapter;  
import java.awt.event.WindowEvent;  
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import java.io.FileWriter;  
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;  
import java.util.logging.Logger;  
  
import javax.swing.JButton;  
import javax.swing.JColorChooser;  
import javax.swing.JComboBox;  
import javax.swing.JDialog;  
import javax.swing.JFileChooser;  
import javax.swing.JFrame;  
import javax.swing.JLabel; 
import javax.swing.JOptionPane;  
import javax.swing.JPanel;  
import javax.swing.JTextField;  
  

public class notebook {  
   // 记事本的具体实现类
   private static final long serialVersionUID = 1L;  
   private  TextArea content;   
   private  String filePath = "";//先让路径为空
   Color color=Color.red;  
   Toolkit toolKit = Toolkit.getDefaultToolkit();  
   Clipboard clipboard = toolKit.getSystemClipboard();  
   public notebook(){  
       //创建一个JFrame对象；并设置相关属性
       final JFrame jf = new JFrame("我的记事本");  
       jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  
       jf.setBounds(100,100,500,500);  
       jf.setResizable(true);  
       jf.setVisible(true);  
       //创建菜单栏
       MenuBar menu = new MenuBar();  
       jf.setMenuBar(menu);  
       //创建并添加文本框
       content = new TextArea("",50,50,TextArea.SCROLLBARS_VERTICAL_ONLY);  
       jf.add(content);  
       content.setVisible(true);      
       content.requestFocusInWindow();  
       //菜单栏添加内容 
       Menu filemenu = new Menu("File(F)");;  
       Menu editmenu = new Menu("Edit(E)");  
       Menu formatmenu = new Menu("Format(O)");  
       Menu viewmenu = new Menu("View(V)");  
       Menu helpmenu = new Menu("Help(H)");  
       menu.add(filemenu);  
       menu .add(editmenu);  
       menu.add(formatmenu);  
       menu.add(viewmenu);  
       menu.add(helpmenu);  
       //创建文件菜单上的各个菜单项并添加到菜单上
       MenuItem newitem = new MenuItem("new(N)");  
       newitem.setShortcut(new MenuShortcut(KeyEvent.VK_N,false));  
       filemenu.add(newitem);  
       MenuItem openitem = new MenuItem("open(O)");  
       openitem.setShortcut(new MenuShortcut(KeyEvent.VK_O,false));  
       filemenu.add(openitem);  
       MenuItem saveitem = new MenuItem("save(S)");  
       saveitem.setShortcut(new MenuShortcut(KeyEvent.VK_S,false));  
       filemenu.add(saveitem);  
       MenuItem saveasitem = new MenuItem("save as(A)");  
       saveasitem.setShortcut(new MenuShortcut(KeyEvent.VK_A,false));  
       filemenu.add(saveasitem);  
       MenuItem setitem = new MenuItem("page setup(U)");  
       setitem.setShortcut(new MenuShortcut(KeyEvent.VK_U,false));  
       filemenu.add(setitem);  
       setitem.setEnabled(false);  
       MenuItem printitem = new MenuItem("print(P)");  
       printitem.setShortcut(new MenuShortcut(KeyEvent.VK_P,false));  
       filemenu.add(printitem);  
       printitem.setEnabled(false);  
       filemenu.addSeparator();  
       MenuItem exititem = new MenuItem("exit(X)");  
       exititem.setShortcut(new MenuShortcut(KeyEvent.VK_X,false));  
       filemenu.add(exititem);  
       //添加监视器来实现文件菜单上的各个菜单项的功能
       //新建菜单项的功能实现
       newitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
               String con = content.getText();  
               if(!con.equals("")){//文本域里文本不为空
                   int result = JOptionPane.showConfirmDialog(  
                           null, ("是否要保存？"),("保存文件..."),JOptionPane.YES_NO_CANCEL_OPTION);  
                   if(result == JOptionPane.NO_OPTION){//不保存
                       content.setText("");  
                   }  
                     
                   else if(result == JOptionPane.CANCEL_OPTION){//取消新建
                   }  
                     
                   else if(result == JOptionPane.YES_OPTION)//选择保存
                   {  
                       JFileChooser jfc = new JFileChooser();//用于选择保存路径的文件名
                       int bcf = jfc.showSaveDialog(jf);  
  
                       if(bcf == JFileChooser.APPROVE_OPTION){  
                                try {  
                                    //保存文件  
                                    BufferedWriter bfw = new BufferedWriter(  
                                            new FileWriter(new File(jfc.getSelectedFile().getAbsolutePath()+".txt")));  
                                    filePath = jfc.getSelectedFile().getAbsolutePath()+".txt";//获取文件保存的路径
                                    bfw.write(con);//向文件写出数据
                                    bfw.flush();  
                                    bfw.close();//关闭输出流
                                } catch (IOException ex) {  
                                    Logger.getLogger(notebook.class.getName()).log(Level.SEVERE, null, ex);  
                                }  
                           new notebook();//新建文本文件 
                       }  
                   }  
               }  
           }  
             
       });  
       //打开菜单项的功能实现
        openitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {   
            FileDialog dialog = new FileDialog(new JFrame(),"open....",FileDialog.LOAD);  
            dialog.setVisible(true);  
            filePath = dialog.getDirectory() + dialog.getFile();  
            System.out.println(filePath);  
            File file = new File(filePath);  
            BufferedReader br = null;  
            StringBuilder sb = new StringBuilder();  
            try{  
                br = new BufferedReader (new InputStreamReader(new FileInputStream(file),"GBK"));  
                String str = null;  
                while ((str = br.readLine()) != null){  
                        sb.append(str).append("\n");  
                    }  
                content.setText(sb.toString());  
            }  
            catch(FileNotFoundException e1){  
                e1.printStackTrace();  
            }  
            catch(IOException e1){  
                e1.printStackTrace();  
            }  
            finally{  
                if(br != null){  
                    try{  
                        br.close();  
                    }  
                    catch(IOException e1){  
                        e1.printStackTrace();  
                    }  
                }  
            }  
        }            
       });  
       //保存菜单项的功能实现
       saveitem.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                FileDialog dialog = new FileDialog(new JFrame(),"save....",FileDialog.SAVE);  
                dialog.setVisible(true);  
                filePath = dialog.getDirectory() + dialog.getFile();  
                if(filePath.equals("")){//没有路径时，就另存为
                       JFileChooser jfc = new JFileChooser();//用于选择保存路径的文件名
                           int bcf = jfc.showSaveDialog(jf);//弹出保存窗口
  
                           if(bcf == JFileChooser.APPROVE_OPTION){  
                                    try {  
                                        //保存文件
                                        BufferedWriter bfw = new BufferedWriter(  
                                                new FileWriter(new File(jfc.getSelectedFile().getAbsolutePath()+".txt")));  
                                        filePath = jfc.getSelectedFile().getAbsolutePath();  
                                        bfw.write(content.getText());//向文件写出数据
                                        bfw.flush();  
                                        bfw.close();//关闭输出流  
                                    } catch (IOException ex) {  
                                        Logger.getLogger(notebook.class.getName()).log(Level.SEVERE, null, ex);  
                                    }  
                           }  
                   }  
                   else{//路径不为空时，保存在原来的路径下 
                       try {  
                           //保存文件  
                           BufferedWriter bfw = new BufferedWriter(  
                                   new FileWriter(  
                                   new File(filePath)));  
                           bfw.write(content.getText());//向文件写出数据  
                           bfw.flush();  
                           bfw.close();//关闭输出流 
                       } catch (IOException ex) {  
                           Logger.getLogger(notebook.class.getName()).log(Level.SEVERE, null, ex);  
                       }  
                   }  
            }  
                
           });  
       //另存为菜单项的功能实现 
       saveasitem.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                 JFileChooser jfc = new JFileChooser();//用于选择保存路径的文件名  
                 int bcf = jfc.showSaveDialog(jf);//弹出保存窗口
  
                 if(bcf == JFileChooser.APPROVE_OPTION){  
                          try {  
                              //保存文件
                              BufferedWriter bfw = new BufferedWriter(  
                                      new FileWriter(new File(jfc.getSelectedFile().getAbsolutePath()+".txt")));  
                              filePath = jfc.getSelectedFile().getAbsolutePath();  
                              bfw.write(content.getText());//向文件写出数据   
                              bfw.flush();  
                              bfw.close();//关闭输出流  
                          } catch (IOException ex) {  
                              Logger.getLogger(notebook.class.getName()).log(Level.SEVERE, null, ex);  
                          }  
                 }  
            }  
                 
           });  
       //页面设置菜单项的功能实现  
       setitem.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                  
            }  
                 
           });  
       //打印菜单项的功能实现 
       printitem.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                  
            }  
                 
           });  
       //退出菜单项的功能实现  
       exititem.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                Object[] options = { "是的，我要退出", "不好意思，点错了" };  
                int option = JOptionPane.showOptionDialog(null, "您确定要退出吗？",  
                         "退出提示....",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,  
                          null,options, options[0]);       
                 if(option == JOptionPane.OK_OPTION){  
                 System.exit(0);  
                 }  
            }  
              
                 
           });  
     //创建编辑菜单上的各个菜单项并添加到菜单上 
       MenuItem undoitem = new MenuItem("undo(U)");  
       undoitem.setShortcut(new MenuShortcut(KeyEvent.VK_Z,false));  
       editmenu.add(undoitem);    
       MenuItem cutitem = new MenuItem("cut(T)");  
       cutitem.setShortcut(new MenuShortcut(KeyEvent.VK_X,false));  
       editmenu.add(cutitem);    
       MenuItem copyitem = new MenuItem("copy(C)");  
       copyitem.setShortcut(new MenuShortcut(KeyEvent.VK_C,false));  
       editmenu.add(copyitem);    
       MenuItem pasteitem = new MenuItem("paste(P)");  
       pasteitem.setShortcut(new MenuShortcut(KeyEvent.VK_V,false));  
       editmenu.add(pasteitem);    
       MenuItem deleteitem = new MenuItem("delete(L)");  
       deleteitem.setShortcut(new MenuShortcut(KeyEvent.VK_DELETE,false));  
       editmenu.add(deleteitem);    
       editmenu.addSeparator();  
       MenuItem finditem = new MenuItem("find(F)");  
       finditem.setShortcut(new MenuShortcut(KeyEvent.VK_F,false));  
       editmenu.add(finditem);    
       MenuItem nextitem = new MenuItem("find next(N)");  
       nextitem.setShortcut(new MenuShortcut(KeyEvent.VK_3,false));  
       editmenu.add(nextitem);    
       MenuItem replaceitem = new MenuItem("replace(R)");  
       replaceitem.setShortcut(new MenuShortcut(KeyEvent.VK_H,false));  
       editmenu.add(replaceitem);    
       MenuItem turntoitem = new MenuItem("go(G)");  
       turntoitem.setShortcut(new MenuShortcut(KeyEvent.VK_G,false));  
       editmenu.add(turntoitem);    
       editmenu.addSeparator();  
       //复选菜单项 
       Menu choicemenu = new Menu("choice(C)");  
       MenuItem allitem = new MenuItem("all(A)");  
       allitem.setShortcut(new MenuShortcut(KeyEvent.VK_A,false));  
       choicemenu.add(allitem);  
       MenuItem fanxiangitem = new MenuItem("backward option(B)");  
       fanxiangitem.setShortcut(new MenuShortcut(KeyEvent.VK_B,false));  
       choicemenu.add(fanxiangitem);  
       MenuItem chieseitem = new MenuItem("chinese(H)");  
       chieseitem.setShortcut(new MenuShortcut(KeyEvent.VK_C,false));  
       choicemenu.add(chieseitem);  
       editmenu.add(choicemenu);    
       //编辑菜单项的时间/日期项
       MenuItem dateitem = new MenuItem("time/date(D)");  
       dateitem.setShortcut(new MenuShortcut(KeyEvent.VK_5,false));  
       editmenu.add(dateitem);   
         
       //添加监听器来实现编辑菜单上的各个菜单项的功能  
       //撤销菜单项的功能实现  
       undoitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
              
        }  
             
       });  
     //剪切菜单项的功能实现  
       cutitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
            String text = content.getSelectedText();  
            StringSelection selection = new StringSelection(text);  
            clipboard.setContents(selection, null);  
            if(text.length() == 0){  
                return;  
            }  
            else{  
            content.replaceRange("", content.getSelectionStart(),content.getSelectionEnd());  
            }  
        }  
             
       });  
     //复制菜单项的功能实现  
       copyitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
            String text = content.getSelectedText();  
            StringSelection selection = new StringSelection(text);  
            clipboard.setContents(selection, null);  
        }  
             
       });  
     //粘贴菜单项的功能实现 
       pasteitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
            Transferable contents = clipboard.getContents(this);  
            String str =null;  
            try {  
                str = (String) contents.getTransferData(DataFlavor.stringFlavor);  
            } catch (UnsupportedFlavorException e1) {  
                e1.printStackTrace();  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
            if (str == null)  
                return;  
            try {  
                content.replaceRange(str,content.getSelectionStart(),content.getSelectionEnd());  
            }   
            catch (Exception e1) {  
                e1.printStackTrace();  
            }  
        }  
             
       });  
     //删除菜单项的功能实现  
       deleteitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
            content.replaceRange("",content.getSelectionStart(),content.getSelectionEnd());  
        }  
             
       });  
     //查找菜单项的功能实现  
       finditem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
            final JDialog dialog = new JDialog(jf,"find...",true);  
            dialog.setBounds(560,250,310,130);  
            JLabel find = new JLabel("print:");  
            final JTextField findtext = new JTextField(1);  
            JButton jbu = new JButton("find");  
            dialog.setLayout(null);  
            find.setBounds(10,30,90,20);  
            findtext.setBounds(100,30,90,20);  
            jbu.setBounds(200,30,80,20);  
            dialog.add(find);  
            dialog.add(findtext);  
            dialog.add(jbu);  
            jbu.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e) {  
                    String text = content.getText();  
                    String str = findtext.getText();  
                    int end = text.length();  
                    int len = str.length();  
                    int start = content.getSelectionEnd();  
                    if(start == end){  
                        start = 0;  
                    }  
                    for(;start<=end-len;start++){  
                        if(text.substring(start,start+len).equals(str)){  
                            content.setSelectionStart(start);  
                            content.setSelectionEnd(start+len);  
                            return;  
                        }  
                    }  
                    //若找不到待查字符串，则将光标置于末尾   
                    content.setSelectionStart(end);  
                    content.setSelectionEnd(end);  
                }  
                     
               });  
            dialog.addWindowListener(new WindowAdapter(){  
                public void windowClosing(WindowEvent e){  
                    dialog.dispose();  
                }  
            });  
            dialog.setResizable(false);  
            dialog.setVisible(true);  
        }  
             
       });  
     //查找下一个菜单项的功能实现  
       nextitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
              
        }  
             
       });  
     //替换菜单项的功能实现 
       replaceitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
              final JDialog dialog = new JDialog(jf,"replace...",true);  
              dialog.setBounds(560,250,310,180);  
              final JLabel tihuan = new JLabel("print former:");  
              JLabel mubiao = new JLabel("print replace:");  
              JTextField jtf1 = new JTextField(10);  
              JTextField jtf2 = new JTextField(10);  
              JButton jb = new JButton("replace");  
              dialog.setLayout(null);  
              tihuan.setBounds(10,30,150,20);  
              mubiao.setBounds(10,70,150,20);  
              jtf1.setBounds(160,30,110,20);  
              jtf2.setBounds(160,70,110,20);  
              jb.setBounds(100,110,80,20);  
              dialog.add(tihuan);  
              dialog.add(mubiao);  
              dialog.add(jtf1);  
              dialog.add(jtf2);  
              dialog.add(jb);  
              final String text = content.getText();  
              final String str1 = tihuan.getText();  
              final String str2 = mubiao.getText();  
              jb.addActionListener(new ActionListener(){  
              public void actionPerformed(ActionEvent e) {  
                if(content.getSelectedText().equals(tihuan.getText())){  
                       content.replaceRange(str2,content.getSelectionStart(),content.getSelectionEnd());  
                     }  
                else {  
                     int end=text.length();  
                     int len=str1.length();  
                     int start=content.getSelectionEnd();  
                     if(start==end) start=0;  
                     for(;start<=end-len;start++){  
                         if(text.substring(start,start+len).equals(str1)){  
                             content.setSelectionStart(start);  
                             content.setSelectionEnd(start+len);  
                             return;  
                         }  
                      }  
                      //若找不到待查字符串，则将光标置于末尾 
                      content.setSelectionStart(end);  
                      content.setSelectionEnd(end);  
                     }  
                      
                   }  
                 
                });  
              dialog.setResizable(false);  
              dialog.setVisible(true);  
        }  
             
       });  
     //转到菜单项的功能实现  
       turntoitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
              
        }  
             
       });  
     //全选菜单项的功能实现
       allitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
            content.selectAll();  
        }  
             
       });  
     //反向选择菜单项的功能实现  
       fanxiangitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
              
        }  
             
       });  
     //选择汉字菜单项的功能实现  
       chieseitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
              
        }  
             
       });  
       //时间菜单项的功能实现  
       dateitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
              
        }  
             
       });  
     //创建格式菜单上的各个菜单项并添加到菜单上  
       CheckboxMenuItem lineturnitem = new CheckboxMenuItem("word wrap(W)");  
       formatmenu.add(lineturnitem);  
       formatmenu.addSeparator();  
       MenuItem worditem = new MenuItem("font(F)");  
       formatmenu.add(worditem);  
       worditem.setEnabled(true);  
       formatmenu.addSeparator();  
       MenuItem coloritem = new MenuItem("color(C)");  
       formatmenu.add(coloritem);  
         
       //添加监听器来实现格式菜单上的各个菜单项的功能  
       //自动换行菜单项的功能实现 
       lineturnitem.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                  
            }  
                 
           });  
       //字体菜单项的功能实现 
       worditem.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                 final JFrame ztsz = new JFrame("font...");//字体设置窗口  
                   //字体  
                   ztsz.setLocation(150, 200);  
                   jf.setEnabled(false);//文本编辑窗体不可用！ 
                   final JComboBox jc = new JComboBox(  
                           GraphicsEnvironment.getLocalGraphicsEnvironment()  
                           .getAvailableFontFamilyNames());  
                   jc.setLocation(30, 80);  
                   Container c = ztsz.getContentPane();  
                   JPanel jp = new JPanel();  
                   jp.add(jc,new FlowLayout());  
  
                 //字形  
                   String[]   faceString={"正常","粗体","斜体","粗斜体"};  
                   String[]   sizeString={"初号","小初","一号","小一","二号","小二",  
                          "三号","小三","四号","小四","五号","小五","六号","小六","七号",  
                          "八号","5","8","9","10","11","12","14","16","18","20","22","24",  
                          "26","28","36","48","72"};  
                   final JComboBox zx = new JComboBox(faceString);  
                   final JComboBox dx = new JComboBox(sizeString);  
                   final JButton sure = new JButton("确定");  
                   final JButton cancel = new JButton("取消");  
                     
                   jp.add(zx);  
                   jp.add(dx);  
                   jp.add(sure);  
                   jp.add(cancel);  
                   c.add(jp);  
  
  
                 //确定  
                   sure.addActionListener(new ActionListener(){  
                       public void actionPerformed(ActionEvent e){//将文本设置成所选的字体  
                               if(!content.getText().equals("")){  
                                   content.setFont(new Font(  
                                       jc.getActionCommand(),zx.getSelectedIndex(),  
                                       dx.getSelectedIndex()));  
                                       jf.setEnabled(true);//文本编辑窗体可用  
                                       ztsz.dispose();  
                               }  
                               else{  
                                   JOptionPane.showMessageDialog(null,  
                                           "您的文本中还没有内容，请输入内容后重新设置！"   
                                           ,"消息...",JOptionPane.INFORMATION_MESSAGE);  
                                   jf.setEnabled(true);  
                                   ztsz.dispose();  
                               }  
                        }  
                   });  
                   cancel.addActionListener(new ActionListener(){//取消  
                       public void actionPerformed(ActionEvent e){  
                               jf.setEnabled(true);//文本编辑窗体可用  
                               ztsz.dispose();//关闭字体设置窗体  
                       }  
                   });  
                   ztsz.setSize(360, 100);//设置窗体长度100和宽度360  
                   ztsz.setVisible(true);//窗体可见  
                   ztsz.setResizable(false);//禁止放大窗体  
            }  
          });  
     //字体颜色菜单项的功能实现  
       coloritem.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                color=JColorChooser.showDialog(jf,"",color);  
                content.setForeground(color);  
            }  
                 
           });  
         
    //添加监听器来实现查看菜单上的各个菜单项的功能  
    //字数统计菜单项的功能实现    
        MenuItem countitem = new MenuItem("word count（C）");    
        viewmenu.add(countitem);  
        countitem.setEnabled(true);  
        countitem.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e) {  
                      
                }  
                  
        });  
    //状态栏菜单项的功能实现    
        MenuItem stateitem = new MenuItem("status（S）");    
        viewmenu.add(stateitem);  
        stateitem.setEnabled(false);  
        stateitem.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                  
            }  
              
        });  
          
    //创建帮助菜单上的各个菜单项并添加到菜单上  
        MenuItem findhelpitem = new MenuItem("find help（H）");    
        helpmenu.add(findhelpitem);  
        findhelpitem.setEnabled(false);  
        helpmenu.addSeparator();  
        MenuItem aboutboxitem = new MenuItem("notebook（A）");    
        helpmenu.add(aboutboxitem);  
        helpmenu.addSeparator();  
        MenuItem writeritem = new MenuItem("author（S）");    
        helpmenu.add(writeritem);  
    //添加监听器来实现帮助菜单上的各个菜单项的功能  
    //查看帮助菜单项的功能实现   
        findhelpitem.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                  
            }  
        });  
    //关于记事本菜单项的功能实现   
        aboutboxitem.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                JOptionPane.showMessageDialog(jf,"本软件由羽洛凡尘制作！\n如需要源代码，随时欢迎联系作者！\n" +  
                        "作者邮箱：18355095692@163.com\nQQ号：1152033931\n本程序基本上实现了Microsoft记事本的功能\n" +  
                        "并新增了“反向选择”，“选择汉字”\n" +  
                        "“字数统计”,“自动保存”等功能  ...\n希望喜欢！\n" +   
                        "最后谢谢您的使用！\n","关于记事本...",JOptionPane.INFORMATION_MESSAGE);  
            }
        });  
    //关于作者菜单项的功能实现 
        writeritem.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                JOptionPane.showMessageDialog(jf,"作者：羽洛凡尘\n性别：男\n籍贯：安徽宿州\n出生日：1996年8月18日\n" +  
                        "本科院校：安徽科技学院\n现居地：滁州\n自我介绍：是呆不是萌\n偶像：周星驰\n" +  
                        "最喜欢的歌手：崔健\n最向往的地方：地狱\n座右铭：是你的总会是你的\n" +    
                        "梦想：做自己喜欢做的事\n","关于作者...",JOptionPane.INFORMATION_MESSAGE);  
            }  
        });  
      //关闭程序事件  
        jf.addWindowListener(new WindowAdapter(){  
            //程序关闭时的方法  
            public void windowClosing(WindowEvent e){  
                int option = JOptionPane.showConfirmDialog(null, "您确定关闭吗？",  
                        "关闭提示....",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);  
                if(option == JOptionPane.OK_OPTION){  
                ((Window) e.getComponent()).dispose();  
                System.exit(0);  
                }  
          }      
        });        
      }  
      public static void main(String[] args){  
          new notebook();  
      }  
   }  
   //新构想：  
   //记事本程序添加音乐盒功能，音乐在后台播放  
   //程序并不实际保存音乐文件，只保存音乐文件的地址  
   //为程序保留20M空间或20M的数据库用于音乐播放  
   //为本程序添加自动保存功能，每一分钟保存一次，后台保存  
   //可能要用到多线程  
   //如果用户选择的保存地址与默认的地址不同，则删除默认地址保存的文件  
   //并且在本次操作中一直使用用户选择的保存地址  
   //但在下次操作时仍使用本程序默认的地址  
   //告诉用户默认的地址，让用户能找到程序为用户保存的文件  
   //最好在“关于记事本”的文档中插入相应的超链接，方便用户  
   //这样做能让用户在出现死机和突然断电时劳动成果不至于付诸东流 