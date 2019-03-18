<%
response.setContentType("image/jpeg");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragrma","no-cache");
response.setDateHeader("Expires",0);
int width = 100;
int height = 25;
int red = (int)(Math.random()*256);
int green = (int)(Math.random()*256);
int blue = (int)(Math.random()*256);
java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
java.awt.Graphics g = img.getGraphics();
g.setColor(new java.awt.Color(0, 0, 127));
g.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 26));
g.fillRect(0,0,width,height);
String verifyCode = "";
for(int i=0; i<4; i++){
	java.util.Random random = new java.util.Random();
	int ic = random.nextInt(58)+65;
	if(ic>90 && ic<97){
		i--;
		continue;
	}
	char c = (char)(ic);
	verifyCode += c;
	red = (int)(Math.random()*256);
	green = (int)(Math.random()*256);
	blue = (int)(Math.random()*256);
	g.setColor(new java.awt.Color(red, green, blue));
	g.drawString(String.valueOf(c), width/6*i+20, height/2+8);
}
session.setAttribute("verifyCode", verifyCode);
g.dispose();
javax.imageio.ImageIO.write(img, "JPEG", response.getOutputStream());
%>