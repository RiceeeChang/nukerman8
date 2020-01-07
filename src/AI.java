public class AI extends Thread{
	Control main;
	int tmp1,tmp2,item,len=1000,tlen=0,count=0,op=0;
	int[] t1 = new int [1000];
	int[] t2 = new int [1000];
	int item1;
	int cf=0;
	int t, n;
	int ttt=0,csa=0;
	public AI(Control main, int n) {
		this.n = n;
		this.main = main;
		
	}
	public void run(){
		int i,j,k=0;
		int x,y,point=1;
		int cf=0,ca=0;
		//main.player_x[1]*40;
		boolean win=false;
		x=main.level.player[n].x;y=main.level.player[n].y;
		while(!win){
			System.out.println("ttt="+ttt);
			if(ttt>0){
				System.out.println("ttt="+ttt);
				int x_b=0,y_b=0;
				for(i=0;i<10;i++){
					if(Level.map[i][main.level.player[n].y]==3){
						x_b=1;
						break;
					}
				}
				for(i=0;i<15;i++){
					if(Level.map[main.level.player[n].x][i]==3){
						y_b=1;
						break;
					}
				}
				if(x_b==0 && ca==0 &&main.level.player[n].x<10 && Level.map[main.level.player[n].x+1][main.level.player[n].y]==0){
					t=main.player_x[n]+40;
					main.key_down[n]=true;
					while(main.player_x[n]!=t){}
					main.key_down[n]=false;
					ca=1;
					ttt=0;
				}
				else if(y_b==0 && ca==1 && main.level.player[n].y>0  && Level.map[main.level.player[n].x][main.level.player[n].y-1]==0){
					t=main.player_y[n]-40;
					main.key_left[n]=true;
					while(main.player_y[n]!=t){}
					main.key_left[n]=false;
					System.out.println("fin");
					ca=2;
					ttt=0;
				}
				else if(x_b==0 &&ca==2 && main.level.player[n].x>=1  && Level.map[main.level.player[n].x-1][main.level.player[n].y]==0){
					t=main.player_x[n]-40;
					main.key_up[n]=true;
					while(main.player_x[n]!=t){}
					main.key_up[n]=false;
					ca=3;
					ttt=0;
				}
				else if(y_b==0 && ca==3 && main.level.player[n].y<14  && Level.map[main.level.player[n].x][main.level.player[n].y+1]==0){
					t=main.player_y[n]+40;
					main.key_right[n]=true;
					while(main.player_y[n]!=t){}
					main.key_right[n]=false;
					ca=4;
					ttt=0;
				}
				else if(ttt>10){
					main.key_space[n]=true;
					try {
						sleep(200);
						} catch (InterruptedException e) {
					// TODO Auto-generated catch block
							e.printStackTrace();
						}
					ca=0;
					ttt=0;
				}
				System.out.println("*********************");
				main.key_space[n]=false;
			}
			len=1000;
			System.out.println("start!!!!");
			for(i=0;i<11 && count==0;i++){
				item1=-5;
				//System.out.println();
				for(j=0;j<15;j++){
						op=0;
						for(k=0;k<cf;k++){
							if(t1[k]==i && t2[k]==j)
								op=1;
						}
						if(k==1000){
							main.key_space[n]=true;
							try {
								sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("*********************");
							main.key_space[n]=false;
						}
					if(op==0 && (Level.map[i][j]==2 || Level.map[i][j]==3 || (Level.map[i][j]>=5 && Level.map[i][j]<=8))){
						if((len>=abs(main.level.player[n].x-i)+abs(main.level.player[n].y-j) && item1<Level.map[i][j] )||(item1==3 && Level.map[i][j]==2 && len>2 ) ){
							len=abs(main.level.player[n].x-i)+abs(main.level.player[n].y-j);
							tmp1=i;
							tmp2=j;
							item=Level.map[i][j];
							item1=item;
							cf=0;
						}
					
					}
				}
			}
			t1[cf]=tmp1;t2[cf]=tmp2;cf++;
			System.out.println("len="+len+" tmp1="+tmp1+" tmp2="+tmp2+" item="+item);
			System.out.println("main.player_x[n]="+main.player_x[n]+" t="+t);
			System.out.println("x="+main.level.player[n].x+" y="+main.level.player[n].y);
			count=0;
			
			switch(item){
				case 2:
					System.out.println("main.level.player[n].x="+(main.level.player[n].x)+" y="+(main.level.player[n].y)+" tmp2="+tmp2+" tmp1="+tmp1+" item="+item);
							if((abs(main.level.player[n].x-tmp1)+abs(main.level.player[n].y-tmp2))==1){
								main.key_space[n]=true;
								try {
									sleep(200);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								main.key_space[n]=false;
							}
							else if((main.level.player[n].x-tmp1)>1 && Level.map[main.level.player[n].x-1][main.level.player[n].y]==0 ){
								t=main.player_x[n]-40;
								main.key_up[n]=true;
								System.out.println("0.0");
								while(main.player_x[n]!=t){}
								System.out.println("123");
								main.key_up[n]=false;
							}
							else if((tmp1-main.level.player[n].x)>1 && Level.map[main.level.player[n].x+1][main.level.player[n].y]==0){
								t=main.player_x[n]+40;
								main.key_down[n]=true;
								System.out.println("2222222222222");
								while(main.player_x[n]!=t){}
								main.key_down[n]=false;
							}
							else if((main.level.player[n].y-tmp2)>1 && Level.map[main.level.player[n].x][main.level.player[n].y-1]==0){
								t=main.player_y[n]-40;
								main.key_left[n]=true;
								System.out.println("333333333333");
								while(main.player_y[n]!=t){}
								main.key_left[n]=false;
							}
							else if((tmp2-main.level.player[n].y)>1 &&  Level.map[main.level.player[n].x][main.level.player[n].y+1]==0){
								t=main.player_y[n]+40;
								System.out.println("123");
								main.key_right[n]=true;
								while(main.player_y[n]!=t){}
								main.key_right[n]=false;
								System.out.println("fin");
							}
							else{
								System.out.println("else !!!! len="+len+" tmp1="+tmp1+" tmp2="+tmp2+" item="+item);
								System.out.println("main.player_x[n]="+main.player_x[n]+" t="+t);
								System.out.println("x="+main.level.player[n].x+" y="+main.level.player[n].y);
								len=1000;
								count=1;
								for(i=0;i<11;i++){
									for(j=0;j<15;j++){
										op=0;
										for(k=0;k<cf;k++){
											if(t1[k]==i && t2[k]==j)
												op=1;
										}
										if(k==1000){
											main.key_space[n]=true;
											try {
												sleep(100);
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											System.out.println("++++++++++++++++++++");
											main.key_space[n]=false;
										}
										if(op==0 && (Level.map[i][j]==2 || Level.map[i][j]==3 || (Level.map[i][j]>=5 && Level.map[i][j]<=8))){
											if(len>abs(main.level.player[n].x-i)+abs(main.level.player[n].y-j) ){
												len=abs(main.level.player[n].x-i)+abs(main.level.player[n].y-j);
												tmp1=i;
												tmp2=j;
												item=Level.map[i][j];
												System.out.println("i="+i+" j="+j+" tmp1="+tmp1+" tmp2="+tmp2+" item="+item);
											}
										}
										
									}
								}
							}
							t1[cf]=tmp1;t2[cf]=tmp2;cf++;
					//}
					break;
				case (-1):
						if(main.level.player[n].x<10 && main.level.player[n].x>=tmp1 && Level.map[main.level.player[n].x+1][main.level.player[n].y]==0){
							t=main.player_x[n]+40;
							main.key_down[n]=true;
							System.out.println("main.player_x[n]="+main.player_x[n]+" t="+t);
							while(main.player_x[n]!=t){}
							System.out.println("123");
							main.key_down[n]=false;
						}
						else if(main.level.player[n].x>=1 && main.level.player[n].x<=tmp1 && Level.map[main.level.player[n].x-1][main.level.player[n].y]==0){
							t=main.player_x[n]-40;
							main.key_up[n]=true;
							while(main.player_x[n]!=t){}
							main.key_up[n]=false;
						}
						else if(main.level.player[n].y<14 && main.level.player[n].y>=tmp2 && Level.map[main.level.player[n].x][main.level.player[n].y+1]==0){
							t=main.player_y[n]+40;
							main.key_right[n]=true;
							while(main.player_y[n]!=t){}
							main.key_right[n]=false;
						}
						else if(main.level.player[n].y>0 && tmp2>=main.level.player[n].y && Level.map[main.level.player[n].x][main.level.player[n].y-1]==0){
							t=main.player_y[n]-40;
							System.out.println("123");
							main.key_left[n]=true;
							while(main.player_y[n]!=t){}
							main.key_left[n]=false;
							System.out.println("fin");
						}
						else{
							System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						}
					//}
					break;
				case (3):
					if(main.level.player[n].x<10 && main.level.player[n].x>=tmp1 && Level.map[main.level.player[n].x+1][main.level.player[n].y]==0){
						t=main.player_x[n]+40;
						main.key_down[n]=true;
						System.out.println("main.player_x[n]="+main.player_x[n]+" t="+t);
						while(main.player_x[n]!=t){}
						System.out.println("123");
						main.key_down[n]=false;
					}
					else if(main.level.player[n].x>0 && main.level.player[n].x<=tmp1 && Level.map[main.level.player[n].x-1][main.level.player[n].y]==0){
						t=main.player_x[n]-40;
						main.key_up[n]=true;
						while(main.player_x[n]!=t){}
						main.key_up[n]=false;
					}
				//}
				//else{
					else if(main.level.player[n].y>0 && tmp2>=main.level.player[n].y && Level.map[main.level.player[n].x][main.level.player[n].y-1]==0){
						t=main.player_y[n]-40;
						System.out.println("123");
						main.key_left[n]=true;
						while(main.player_y[n]!=t){}
						main.key_left[n]=false;
						System.out.println("fin");
					}
					else if(main.level.player[n].y<14 && main.level.player[n].y>=tmp2 && Level.map[main.level.player[n].x][main.level.player[n].y+1]==0){
						t=main.player_y[n]+40;
						main.key_right[n]=true;
						System.out.println("123");
						while(main.player_y[n]!=t){}
						main.key_right[n]=false;
					}
					else{
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					}
				if(main.level.player[n].x<10 && main.level.player[n].x>=tmp1 && Level.map[main.level.player[n].x+1][main.level.player[n].y]==0){
					t=main.player_x[n]+40;
					main.key_down[n]=true;
					System.out.println("main.player_x[n]="+main.player_x[n]+" t="+t);
					while(main.player_x[n]!=t){}
					System.out.println("123");
					main.key_down[n]=false;
				}
				else if(main.level.player[n].x>0 && main.level.player[n].x<=tmp1 && Level.map[main.level.player[n].x-1][main.level.player[n].y]==0){
					t=main.player_x[n]-40;
					main.key_up[n]=true;
					while(main.player_x[n]!=t){}
					main.key_up[n]=false;
				}
			//}
			//else{
				else if(main.level.player[n].y<14 && main.level.player[n].y>=tmp2 && Level.map[main.level.player[n].x][main.level.player[n].y+1]==0){
					t=main.player_y[n]+40;
					main.key_right[n]=true;
					System.out.println("123");
					while(main.player_y[n]!=t){}
					main.key_right[n]=false;
				}
				else if(main.level.player[n].y>0 && tmp2>=main.level.player[n].y && Level.map[main.level.player[n].x][main.level.player[n].y-1]==0){
					t=main.player_y[n]-40;
					System.out.println("123");
					main.key_left[n]=true;
					while(main.player_y[n]!=t){}
					main.key_left[n]=false;
					System.out.println("fin");
				}
				else{
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
				//}
				break;
				default:
					//while(abs(main.level.player[n].x-tmp1)+abs(main.level.player[n].y-tmp2)!=0){
							if((main.level.player[n].x-tmp1)>0 && (Level.map[main.level.player[n].x-1][main.level.player[n].y]==0 ||Level.map[main.level.player[n].x-1][main.level.player[n].y]==item)){
								t=main.player_x[n]-40;
								main.key_up[n]=true;
								System.out.println("123");
								while(main.player_x[n]!=t){}
								System.out.println("123");
								main.key_up[n]=false;
							}
							else if((tmp1-main.level.player[n].x)>0 && (Level.map[main.level.player[n].x+1][main.level.player[n].y]==0 ||Level.map[main.level.player[n].x+1][main.level.player[n].y]==item)){
								t=main.player_x[n]+40;
								main.key_down[n]=true;
								while(main.player_x[n]!=t){}
								main.key_down[n]=false;
							}
							else if((main.level.player[n].y-tmp2)>0 && (Level.map[main.level.player[n].x][main.level.player[n].y-1]==0 ||Level.map[main.level.player[n].x][main.level.player[n].y-1]==item)){
								t=main.player_y[n]-40;
								main.key_left[n]=true;
								while(main.player_y[n]!=t){}
								main.key_left[n]=false;
							}
							else if((tmp2-main.level.player[n].y)>0 && (Level.map[main.level.player[n].x][main.level.player[n].y+1]==0 ||Level.map[main.level.player[n].x][main.level.player[n].y+1]==item)){
								t=main.player_y[n]+40;
								System.out.println("123");
								main.key_right[n]=true;
								while(main.player_y[n]!=t){}
								main.key_right[n]=false;
								System.out.println("fin");
							}
							else{
								System.out.println("else !!!! len="+len+" tmp1="+tmp1+" tmp2="+tmp2+" item="+item);
								System.out.println("main.player_x[n]="+main.player_x[n]+" t="+t);
								System.out.println("x="+main.level.player[n].x+" y="+main.level.player[n].y+" cf="+cf);
								len=1000;
								count=1;
								for(i=0;i<11;i++){
									for(j=0;j<15;j++){
										op=0;
										for(k=0;k<cf+1;k++){
											if(t1[k]==i && t2[k]==j)
												op=1;
										}
										if(k==1000){
											main.key_space[n]=true;
											try {
												sleep(100);
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											System.out.println("++++++++++++++++++++");
											main.key_space[n]=false;
										}
										if(op==0 && (Level.map[i][j]==2 || Level.map[i][j]==3 || (Level.map[i][j]>=5 && Level.map[i][j]<=8))){
											if(len>abs(main.level.player[n].x-i)+abs(main.level.player[n].y-j) ){
												len=abs(main.level.player[n].x-i)+abs(main.level.player[n].y-j);
												tmp1=i;
												tmp2=j;
												item=Level.map[i][j];
												System.out.println("i="+i+" j="+j+" tmp1="+tmp1+" tmp2="+tmp2+" item="+item);
											}
										}
										
									}
								}
							}
							t1[cf]=tmp1;t2[cf]=tmp2;cf++;
				//}
				break;
				
			}
			if(point==0){
				if(x==main.level.player[n].x && y==main.level.player[n].y){
					ttt++;
				}
				x=main.level.player[n].x;y=main.level.player[n].y;
				point=1;
			}
			else{
				point=0;
			}
				
		}

	}
	private int abs(int num) {
		if(num>0)
			return num;
		else return -num;
	}
}
