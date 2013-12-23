package Automata.Figures;

import Automata.Model.FSM_Model;
import CH.ifa.draw.figure.connection.LineConnection;
import CH.ifa.draw.framework.Figure;

import java.awt.*;

/**
 * Author: Tiago de França Queiroz
 * Date: 21/12/13
 * <p/>
 * Copyright Tiago de França Queiroz, 2013.
 * <p/>
 * This file is part of Automata.Automata.Figures.
 * <p/>
 * Automata.Automata.Figures is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * Automata.Automata.Figures is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with Automata.Automata.Figures. If not, see <http://www.gnu.org/licenses/>.
 */
public class ZeroConnection extends LineConnection
{
	public ZeroConnection()
	{
		super();
		fFrameColor = Color.red;
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
		System.out.printf("0-handleConnect: %s, %s\n", start, end);

		FSM_Model model = FSM_Model.getInstance();

		model.connectZero((CountingFigure) start, (CountingFigure) end);
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
		System.out.printf("0-handleDisconnect : %s, %s\n", start, end);
	}

	public String toString()
	{
		return String.format("Zero: %s -> %s\n", startFigure(), endFigure());
	}
}
