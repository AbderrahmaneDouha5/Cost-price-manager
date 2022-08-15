/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prixdrevien.images;

import javax.swing.JLabel;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class SvgLabel extends JLabel {
    FlatSVGIcon svg ;
    public void setSvg(String url, int width, int height){
        svg = new FlatSVGIcon(url, width, height);
        
        setIcon(svg);
        
    }
}
