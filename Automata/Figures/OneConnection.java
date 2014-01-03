package Automata.Figures;

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
public class OneConnection extends LineConnection
{
	public OneConnection()
	{
		super();
		fFrameColor = Color.blue;
		setStartDecoration(null);
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

		model.connectOne(start, end);

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

		if(snode.getOne() != null)
			return false;
		else
			return super.canConnect(start, end);
	}

	public String toString()
	{
		return String.format("One: %s -> %s\n", startFigure(), endFigure());
	}
}
