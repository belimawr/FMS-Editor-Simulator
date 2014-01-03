package Automata.Figures;

import Automata.Decorators.BorderArrowTip;
import Automata.Model.FSM_Model;
import Automata.Model.FSM_Node;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.Figure;

import java.awt.*;

/**
 * Author: Tiago de França Queiroz
 * Date: 21/12/13
 *
 * Copyright Tiago de França Queiroz, 2013.

 * This file is part of Automata.
 *
 * Automata is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Automata is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Automata. If not, see <http://www.gnu.org/licenses/>.
 */
public class ZeroConnection extends LineConnection
{
	public ZeroConnection()
	{
		super();
		fFrameColor = Color.red;
		setStartDecoration(null);
		/*	    fAngle = 0.40;//0.35;
	    fOuterRadius = 8;//15;
	    fInnerRadius = 8;//12;*/
		setEndDecoration(new BorderArrowTip(0.4, 20, 20));
	}

	/*
     * This method is called by JHD whenever an
     * connection is made.
     *
     * I use this to add connections to the model
     * data structure
     */
	@Override
	protected void handleConnect(Figure start, Figure end)
	{
		super.handleConnect(start, end);

		FSM_Model model = FSM_Model.getInstance();

		model.connectZero(start, end);

		/* Add the FSM_Model as a listener to this figure/connection */
		this.addFigureChangeListener(model);
	}

	/*
     * This method is called by JHD whenever an
	 * connection is delete.
     *
     * I use this to remove connections to the model
     * data structure
     */
	@Override
	protected void handleDisconnect(Figure start, Figure end)
	{
		super.handleDisconnect(start, end);
	}

	/*
     * If the connection already exists, does not allow
     * create a new one.
     *
     * Replace the connection is not easy because
     * there are too many indirect calls. :(
     */
	@Override
	public boolean canConnect(Figure start, Figure end)
	{
		if(start instanceof TextFigure ||
		   end instanceof TextFigure)
			return false;

		FSM_Node snode = FSM_Model.getInstance().getNode(start);

		if(snode.getZero() != null)
			return false;
		else
			return super.canConnect(start, end);
	}

	/* TODO: Maybe create an abstract class to put those two methods */
	@Override
	public void draw(Graphics g)
	{
		g.setColor(Color.green);
		Point p1, p2;
		for (int i = 0; i < fPoints.size() - 1; i++)
		{
			p1 = fPoints.elementAt(i);
			p2 = fPoints.elementAt(i + 1);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
		g.setColor(getFrameColor());
		decorate(g);
	}

	/*
	 * Private method needs to be copied
	 * when extending class...
	 */
	private void decorate(Graphics g)
	{
		if (fStartDecoration != null)
		{
			Point p1 = fPoints.elementAt(0);
			Point p2 = fPoints.elementAt(1);
			fStartDecoration.draw(g, p1.x, p1.y, p2.x, p2.y);
		}
		if (fEndDecoration != null)
		{
			Point p3 = fPoints.elementAt(fPoints.size() - 2);
			Point p4 = fPoints.elementAt(fPoints.size() - 1);
			fEndDecoration.draw(g, p4.x, p4.y, p3.x, p3.y);
		}
	}

	public String toString()
	{
		return String.format("Zero: %s -> %s\n", startFigure(), endFigure());
	}
}
