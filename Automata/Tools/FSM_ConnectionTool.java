package Automata.Tools;

import CH.ifa.draw.framework.*;
import CH.ifa.draw.tool.AbstractTool;
import CH.ifa.draw.util.Geom;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

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
 * along with Automata. If not, see <http://www.gnu.org/licenses/>.
 */

/* Attributes are private, I had to copy the whole class ... */
public class FSM_ConnectionTool
		extends AbstractTool
{
	/**
	 * the anchor point of the interaction
	 */
	private Connector fStartConnector;
	private Connector   fEndConnector;
	private Connector   fConnectorTarget = null;

	private Figure fTarget = null;

	/**
	 * the currently created figure
	 */
	private ConnectionFigure  fConnection;

	/**
	 * the currently manipulated connection point
	 */
	private int  fSplitPoint;

	/**
	 * the currently edited connection
	 */
	private ConnectionFigure  fEditedConnection = null;

	/**
	 * the prototypical figure that is used to create new
	 * connections.
	 */
	private ConnectionFigure  fPrototype;


	public FSM_ConnectionTool(DrawingView view, ConnectionFigure prototype)
	{
		super(view);
		fPrototype = prototype;
	}

	/**
	 * Creates the ConnectionFigure. By default the figure prototype is
	 * cloned.
	 */
	protected ConnectionFigure createConnection()
	{
		return (ConnectionFigure)fPrototype.clone();
	}

	/**
	 * Gets the currently created figure
	 */
	protected ConnectionFigure createdFigure()
	{
		return fConnection;
	}

	@Override
	public void deactivate()
	{
		super.deactivate();
		if (fTarget != null)
			fTarget.connectorVisibility(false);
	}

	protected Figure findConnectableFigure(int x, int y, Drawing drawing)
	{
		Enumeration<Figure> k = drawing.figuresReverse();
		while (k.hasMoreElements()) {
			Figure figure = k.nextElement();
			if (!figure.includes(fConnection) && figure.canConnect())
			{
				if (figure.containsPoint(x, y))
					return figure;
			}
		}
		return null;
	}

	/**
	 * Finds an existing connection figure.
	 */
	protected ConnectionFigure findConnection(int x, int y, Drawing drawing)
	{
		Enumeration<Figure> k = drawing.figuresReverse();
		while (k.hasMoreElements())
		{
			Figure figure = k.nextElement();
			figure = figure.findFigureInside(x, y);
			if (figure != null && (figure instanceof ConnectionFigure))
				return (ConnectionFigure)figure;
		}
		return null;
	}

	/**
	 * Finds a connection start figure.
	 */
	protected Figure findConnectionStart(int x, int y, Drawing drawing)
	{
		Figure target = findConnectableFigure(x, y, drawing);
		if ((target != null) && target.canConnect())
			return target;
		return null;
	}

	protected Connector findConnector(int x, int y, Figure f)
	{
		return f.connectorAt(x, y);
	}

	/**
	 * Finds a connectable figure target.
	 */
	protected Figure findSource(int x, int y, Drawing drawing)
	{
		return findConnectableFigure(x, y, drawing);
	}

	/**
	 * Finds a connectable figure target.
	 */
	protected Figure findTarget(int x, int y, Drawing drawing)
	{
		Figure target = findConnectableFigure(x, y, drawing);
		Figure start = fStartConnector.owner();

		if (target != null
				&& fConnection != null
				&& target.canConnect()
//				&& !target.includes(start) /* Allow self connection */
				&& fConnection.canConnect(start, target))
			return target;
		return null;
	}

	protected Connector getEndConnector()
	{
		return fEndConnector;
	}

	protected Connector getStartConnector()
	{
		return fStartConnector;
	}

	protected Connector getTarget()
	{
		return fConnectorTarget;
	}

	/**
	 * Manipulates connections in a context dependent way. If the
	 * mouse down hits a figure start a new connection. If the mousedown
	 * hits a connection split a segment or join two segments.
	 */
	@Override
	public void mouseDown(MouseEvent e, int x, int y)
	{
		int ex = e.getX();
		int ey = e.getY();
		fTarget = findConnectionStart(ex, ey, drawing());
		if (fTarget != null)
		{
			fStartConnector = findConnector(ex, ey, fTarget);
			if (fStartConnector != null)
			{
				Point p = new Point(ex, ey);
				fConnection = createConnection();
				fConnection.startPoint(p.x, p.y);
				fConnection.endPoint(p.x, p.y);
				view().add(fConnection);
			}
		}
		else
		{
			ConnectionFigure connection = findConnection(ex, ey, drawing());
			if (connection != null)
			{
				if (!connection.joinSegments(ex, ey))
				{
					fSplitPoint = connection.splitSegment(ex, ey);
					fEditedConnection = connection;
				}
				else
				{
					fEditedConnection = null;
				}
			}
		}
	}

	/**
	 * Adjust the created connection or split segment.
	 */
	@Override
	public void mouseDrag(MouseEvent e, int x, int y)
	{
		Point p = new Point(e.getX(), e.getY());
		if (fConnection != null)
		{
			trackConnectors(e, x, y);
			if (fConnectorTarget != null)
				p = Geom.center(fConnectorTarget.displayBox());
			fConnection.endPoint(p.x, p.y);
		}
		else if (fEditedConnection != null)
		{
			Point pp = new Point(x, y);
			fEditedConnection.setPointAt(pp, fSplitPoint);
		}
	}

	/**
	 * Handles mouse move events in the drawing view.
	 */
	@Override
	public void mouseMove(MouseEvent e, int x, int y)
	{
		trackConnectors(e, x, y);
	}

	/**
	 * Connects the figures if the mouse is released over another
	 * figure.
	 */
	@Override
	public void mouseUp(MouseEvent e, int x, int y)
	{
		Figure c = null;
		if (fStartConnector != null)
			c = findTarget(e.getX(), e.getY(), drawing());

		if (c != null)
		{
			fEndConnector = findConnector(e.getX(), e.getY(), c);
			if (fEndConnector != null)
			{
				fConnection.connectStart(fStartConnector);
				fConnection.connectEnd(fEndConnector);
				fConnection.updateConnection();
			}
		}
		else if (fConnection != null)
			view().remove(fConnection);

		fConnection = null;
		fStartConnector = fEndConnector = null;
		// MIW Uncomment to only allow one connection at a time
		// editor().toolDone();
	}

	protected void trackConnectors(MouseEvent e, int x, int y)
	{
		Figure c;

		if (fStartConnector == null)
			c = findSource(x, y, drawing());
		else
			c = findTarget(x, y, drawing());

		// track the figure containing the mouse
		if (c != fTarget)
		{
			if (fTarget != null)
				fTarget.connectorVisibility(false);
			fTarget = c;
			if (fTarget != null)
				fTarget.connectorVisibility(true);
		}

		Connector cc = null;
		if (c != null)
			cc = findConnector(e.getX(), e.getY(), c);
		if (cc != fConnectorTarget)
			fConnectorTarget = cc;

		view().checkDamage();
	}
}
