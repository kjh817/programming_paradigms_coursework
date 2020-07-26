class Mario
{
    int x;
    int y;
    double verticalVelocity;
    int frame = 0;

    final int width = 57;
    final int height = 96;
    int preX;
    int preY;

    int jumpCounter = 0;

    Mario()
    {
        x = 0;
        y = 500;
        verticalVelocity = 0;
    }


    void update()
    {
    		// perform gravity
    		
   		verticalVelocity += 4.9;
        this.y += verticalVelocity;

        
        // create ground
        if(this.y >= 500)
        {
            this.verticalVelocity = 0;
            this.y = 500;
            jumpCounter = 0;
        }
        
        
        
        System.out.println("VV" + verticalVelocity);

        imageUpdate();
    }

    void jump()
    {
    		if(jumpCounter < 5)
    		{
    			verticalVelocity += -12.4;
    			jumpCounter++;
    		}
    }

    void imageUpdate()
    {
    		frame = (Math.abs(x)/10) % 5;
    }


    void moveMario(int speed)
    {
    		this.x = this.x + speed;

    		if(this.x < -200)
    		{
    			this.x = -200;
    		}
    }


    void rememberState()
    {
        this.preY = this.y;
        this.preX = this.x;
    }




    	void getOutOfTube(Tube t)
    {
        if((this.x + this.width > t.x) && (this.preX <= t.x)) // if he crossed the left side of the tube
        {	
        		if((this.y + this.height > t.y) 
        				&& (this.preY <= t.y))
        		{
        			this.y = t.y - this.height;
        		}
        		else
        		{
        			this.x = t.x - this.width - 1;
        		}
        }
        else if((this.x < t.x + t.width) && (this.preX >= t.x + t.width)) // if he crossed the right side
        {
            this.x = t.x + t.width + 1;
        }

        else if((this.y + this.height > t.y) && (this.preY <= t.y)) // if he crossed the top of the tube
        {
            this.y = t.y - this.height;
        }
        else if((this.y < t.y + t.height) && (this.preY >= t.y + t.height)) // if he crossed the bottom
        {
            this.y = t.y + t.height + this.height;
        }
        else
            System.out.println("How did I get in here?");
    }
}
