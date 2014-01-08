package Automata.Figures;

import Automata.Handlers.WhiteNullHandler;
import Automata.Model.FSM_Model;
import Automata.Model.FSM_Node;
import CH.ifa.draw.figure.ArrowTip;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.Connector;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureChangeEvent;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.locator.RelativeLocator;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.QuadCurve2D;
import java.util.Vector;

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
	private final int ARC_SIZE = 30;
	private Rectangle displaybox;
	private boolean loop;

	public ZeroConnection()
	{
		super();
		loop = false;
		fFrameColor = Color.red;
		setStartDecoration(null);
		setEndDecoration(new ArrowTip(0.4, 15, 15));
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
	 * If the connection already exists, does not allow
	 * create a new one.
	 */
	@Override
	public boolean canConnect(Figure start, Figure end)
	{
		if(start instanceof TextFigure || end instanceof TextFigure)
			return false;

		FSM_Node snode = FSM_Model.getInstance().getNode(start);

		if(snode.getZero() != null)
			return false;
		else
			return super.canConnect(start, end);
	}

	public String toString()
	{
		return String.format("Zero: %s -> %s\n", startFigure(), endFigure());
	}

	@Override
	public Vector<Handle> handles()
	{
		if(loop)
		{
			Vector<Handle> handles = new Vector<Handle>();
			handles.addElement(new WhiteNullHandler(this, RelativeLocator.north()));
			handles.addElement(new WhiteNullHandler(this, RelativeLocator.east()));
			handles.addElement(new WhiteNullHandler(this, RelativeLocator.west()));
			return handles;
		}
		else
			return super.handles();
	}

	@Override
	public void draw(Graphics g)
	{
		if(loop)
		{
			Rectangle r = fEnd.owner().displayBox();
			Point centre = fEnd.owner().center();
			int radius = r.height/2;

			QuadCurve2D c = new QuadCurve2D.Float(centre.x - radius,
			                                      centre.y,
			                                      centre.x - radius*1.5f,
			                                      centre.y - radius*3,
			                                      centre.x,
			                                      centre.y - radius);

			Point p1 = new Point(centre.x - radius, centre.y);
			Point p2 = new Point(p1.x + 7, p1.y - 15);
			Point p3 = new Point(p1.x - 7, p1.y - 15);

			Polygon triangle = new Polygon();
			triangle.addPoint(p1.x, p1.y);
			triangle.addPoint(p2.x, p2.y);
			triangle.addPoint(p3.x, p3.y);

			g.setColor(fFrameColor);
			((Graphics2D) g).draw(c);
			g.fillPolygon(triangle);
		}
		else
			super.draw(g);
	}

	@Override
	public Rectangle displayBox()
	{
		if(loop)
		{
			Point centre = fEnd.owner().center();
			Rectangle rect = new Rectangle(centre);

			/* Black magic tuning*/
			rect.add(centre.x - 37, centre.y - 58);
			return rect;
		}
		else
			return super.displayBox();
	}

	@Override
	public void figureInvalidated(FigureChangeEvent e)
	{
		if(loop)
		{
			Rectangle rect = e.getInvalidatedRectangle();

			rect.add(displayBox());
			super.figureInvalidated(new FigureChangeEvent(e.getFigure(), rect));
		}
		super.figureInvalidated(e);
	}

	@Override
	public void connectEnd(Connector end)
	{
		super.connectEnd(end);
		if(fStart.owner() == fEnd.owner())
			loop = true;
	}


	/**
	 * If true, the figure is selected :D \o/
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public boolean containsPoint(int x, int y)
	{
		if(loop)
			return displayBox().contains(x, y);
		else
			return super.containsPoint(x, y);
	}
}
