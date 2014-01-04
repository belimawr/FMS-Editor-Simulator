package Automata.Figures;

import Automata.Handlers.WhiteNullHandler;
import Automata.Model.FSM_Model;
import Automata.Model.FSM_Node;
import CH.ifa.draw.figure.ArrowTip;
import CH.ifa.draw.figure.TextFigure;
import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.handle.PolyLineHandle;
import CH.ifa.draw.locator.RelativeLocator;

import java.awt.*;
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
	public ZeroConnection()
	{
		super();
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

	public String toString()
	{
		return String.format("Zero: %s -> %s\n", startFigure(), endFigure());
	}

	@Override
	public Vector<Handle> handles()
	{
		Vector<Handle> handles = new Vector<Handle>(fPoints.size());
		handles.addElement(new WhiteNullHandler(this, RelativeLocator.center()));
		for (int i = 1; i < fPoints.size() - 1; i++)
			handles.addElement(new PolyLineHandle(this, locator(i), i));
		return handles;
	}
}
