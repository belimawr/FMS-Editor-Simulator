package Automata.Figures;

import Automata.Connector.CountingFigureConnectorHandler;
import Automata.Handlers.WhiteNullHandler;
import CH.ifa.draw.connector.LocatorConnector;
import CH.ifa.draw.figure.EllipseFigure;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.framework.Connector;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.locator.RelativeLocator;
import CH.ifa.draw.util.Geom;

import java.awt.*;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Author: Tiago de França Queiroz
 * Date: 21/12/13
 *
 * Copyright Tiago de França Queiroz, 2013.
 *
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
 * along with Automata If not, see <http://www.gnu.org/licenses/>.
 */
public class CountingFigure extends EllipseFigure
{
	private Rectangle display_box;
	static int counter = 0;
	private int my_number;

	/* Connectors stuff */
	private Vector<LocatorConnector> fConnectors = null;
	private boolean fConnectorsVisible = false;

	public CountingFigure()
	{
		super();
		my_number = counter++;
		setAttribute("FillColor", Color.cyan);
	}

	/**
	 * Translation method.
	 *
	 * @param x
	 * @param y
	 */
	@Override
	protected void basicMoveBy(int x, int y)
	{
		display_box.translate(x, y);
	}

	/**
	 * Returns a copy of its display box
	 *
	 * @return
	 */
	@Override
	public Rectangle displayBox()
	{
		return new Rectangle(
				display_box.x,
				display_box.y,
				display_box.width,
				display_box.height);
	}

	/**
	 * Fix the size of the state
	 *
	 * @param origin Origin of the display box
	 * @param corner Opposite corner of the display box
	 */
	@Override
	public void basicDisplayBox(Point origin, Point corner)
	{
		display_box = new Rectangle(origin);

		display_box.setSize(65, 65);
	}

	@Override
	public Vector<Handle> handles()
	{
		ConnectionFigure one = new OneConnection();
		ConnectionFigure zero = new ZeroConnection();
		Vector<Handle> handles = new Vector<Handle>();
		handles.addElement(new WhiteNullHandler(this, RelativeLocator.southEast()));
		handles.addElement(new WhiteNullHandler(this, RelativeLocator.northEast()));
		handles.addElement(new WhiteNullHandler(this, RelativeLocator.southWest()));
		handles.addElement(new WhiteNullHandler(this, RelativeLocator.northWest()));

		handles.addElement(new CountingFigureConnectorHandler(this, RelativeLocator.east(), one));
		handles.addElement(new CountingFigureConnectorHandler(this, RelativeLocator.west(), one));
		handles.addElement(new CountingFigureConnectorHandler(this, RelativeLocator.south(), zero));
		handles.addElement(new CountingFigureConnectorHandler(this, RelativeLocator.north(), zero));
		return handles;
	}

	public int getMy_number()
	{
		return my_number;
	}

	public String toString()
	{
		return String.format("S%d", my_number);
	}

	/*
	 * Connector stuff to allow a 'nice'
	 * self connection.
	 *
	 * Everything is copy past
	 * from NodeFigure.
	 */
	private Connector findConnector(int x, int y)
	{
		// return closest connector
		long min = Long.MAX_VALUE;
		Connector closest = null;
		Enumeration<LocatorConnector> e = connectors().elements();
		while (e.hasMoreElements())
		{
			Connector c = e.nextElement();
			Point p2 = Geom.center(c.displayBox());
			long d = Geom.length2(x, y, p2.x, p2.y);
			if (d < min) {
				min = d;
				closest = c;
			}
		}
		return closest;
	}

	private void drawConnectors(Graphics g)
	{
		if (fConnectorsVisible)
		{
			Enumeration<LocatorConnector> e = connectors().elements();
			while (e.hasMoreElements())
				e.nextElement().draw(g);
		}
	}

	private void createConnectors()
	{
		fConnectors = new Vector<LocatorConnector>(4);
		fConnectors.addElement(new LocatorConnector(this, RelativeLocator.north()) );
		fConnectors.addElement(new LocatorConnector(this, RelativeLocator.south()) );
		fConnectors.addElement(new LocatorConnector(this, RelativeLocator.west()) );
		fConnectors.addElement(new LocatorConnector(this, RelativeLocator.east()) );
	}

	@Override
	public void connectorVisibility(boolean isVisible)
	{
		fConnectorsVisible = isVisible;
		invalidate();
	}

	private Vector<LocatorConnector> connectors()
	{
		if (fConnectors == null)
			createConnectors();
		return fConnectors;
	}

	@Override
	public Connector connectorAt(int x, int y)
	{
		return findConnector(x, y);
	}
}
